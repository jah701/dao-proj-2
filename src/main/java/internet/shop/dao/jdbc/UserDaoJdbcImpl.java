package internet.shop.dao.jdbc;

import internet.shop.dao.UserDao;
import internet.shop.exceptions.DataProcessingException;
import internet.shop.lib.Dao;
import internet.shop.model.Role;
import internet.shop.model.User;
import internet.shop.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Dao
public class UserDaoJdbcImpl implements UserDao {
    @Override
    public Optional<User> findByLogin(String login) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM users WHERE login = ? AND deleted = false;");
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = getUserFromResultSet(resultSet);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find user with login: " + login, e);
        }
        return Optional.empty();
    }

    @Override
    public User create(User user) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO users(login, username, password) "
                            + "VALUES (?, ?, ?);", PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getName());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getLong(1));
            }
            return addRole(user);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create new user", e);
        }
    }

    @Override
    public Optional<User> get(Long userId) {
        User user = new User();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM users u "
                            + "INNER JOIN users_roles ur ON u.user_id = ur.user_id "
                            + "WHERE u.deleted = false AND u.user_id = ?;");
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = getUserFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find user with id: " + userId, e);
        }
        user.setRoles(getUserRoles(userId));
        return Optional.of(user);
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM users WHERE deleted = false;");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = getUserFromResultSet(resultSet);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all users list", e);
        }
    }

    @Override
    public User update(User user) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE users SET login = ?, username = ?, password = ? "
                            + "WHERE deleted = false AND user_id = ?");
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getName());
            statement.setString(3, user.getPassword());
            statement.setLong(4, user.getId());
            statement.executeUpdate();
            clearRoles(user);
            addRole(user);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update user with id: " + user.getId(), e);
        }
        return user;
    }

    @Override
    public boolean delete(Long userId) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE users SET deleted = true WHERE user_id = ?");
            statement.setLong(1, userId);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete user with id: " + userId, e);
        }
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        Long userId = resultSet.getLong("user_id");
        String login = resultSet.getString("login");
        String userName = resultSet.getString("username");
        String password = resultSet.getString("password");
        return new User(userId, login, userName, password);
    }

    private User addRole(User user) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO users_roles(user_id, role_id) VALUES (?,"
                            + "(SELECT role_id FROM roles WHERE role_name = ?));");
            for (Role role : user.getRoles()) {
                statement.setLong(1, user.getId());
                statement.setString(2, role.getRoleName().name());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't clear user roles", e);
        }
        return user;
    }

    private boolean clearRoles(User user) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM users_roles WHERE user_id = ?;");
            statement.setLong(1, user.getId());
            return 1 == statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't clear user roles", e);
        }
    }

    private Set<Role> getUserRoles(Long userId) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT r.role_id, role_name FROM roles r INNER JOIN users_roles ur "
                             + "ON ur.role_id = r.role_id WHERE ur.user_id = ?")) {
            statement.setLong(1, userId);
            ResultSet rs = statement.executeQuery();
            Set<Role> roles = new HashSet<>();
            while (rs.next()) {
                Long roleId = rs.getLong("role_id");
                String roleName = rs.getString("role_name");
                Role role = Role.of(roleName);
                role.setId(roleId);
                roles.add(role);
            }
            return roles;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get roles of user with ID = "
                    + userId, e);
        }
    }
}
