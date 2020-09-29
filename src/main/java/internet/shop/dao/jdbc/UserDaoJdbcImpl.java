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
        String query = "SELECT * FROM users WHERE login = ? AND deleted = false;";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = getUserFromResultSet(resultSet);
                statement.close();
                user.setRoles(getUserRoles(user.getId(), connection));
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find user with login: " + login, e);
        }
        return Optional.empty();
    }

    @Override
    public User create(User user) {
        String query = "INSERT INTO users(login, username, password, salt) VALUES (?, ?, ?, ?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                        query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getName());
            statement.setString(3, user.getPassword());
            statement.setBytes(4, user.getSalt());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getLong(1));
            }
            statement.close();
            return addRole(user, connection);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create new user", e);
        }
    }

    @Override
    public Optional<User> get(Long userId) {
        String query = "SELECT * FROM users u "
                + "INNER JOIN users_roles ur ON u.user_id = ur.user_id "
                + "WHERE u.deleted = false AND u.user_id = ?;";
        User user = null;
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = getUserFromResultSet(resultSet);
            }
            statement.close();
            user.setRoles(getUserRoles(userId, connection));
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find user with id: " + userId, e);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> getAll() {
        String query = "SELECT * FROM users WHERE deleted = false;";
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
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
        String query = "UPDATE users SET login = ?, username = ?, password = ?, salt = ? "
                + "WHERE deleted = false AND user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getName());
            statement.setString(3, user.getPassword());
            statement.setBytes(4, user.getSalt());
            statement.setLong(5, user.getId());
            statement.executeUpdate();
            statement.close();
            clearRoles(user, connection);
            addRole(user, connection);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update user with id: " + user.getId(), e);
        }
        return user;
    }

    @Override
    public boolean delete(Long userId) {
        String query = "UPDATE users SET deleted = true WHERE user_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete user with id: " + userId, e);
        }
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        Long userId = resultSet.getLong("user_id");
        String login = resultSet.getString("login");
        String userName = resultSet.getString("username");
        String password = resultSet.getString("password");
        byte[] salt = resultSet.getBytes("salt");
        return new User(userId, login, userName, password, salt);
    }

    private User addRole(User user, Connection connection) {
        String query = "INSERT INTO users_roles(user_id, role_id) VALUES (?, "
                + "(SELECT role_id FROM roles WHERE role_name = ?));";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
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

    private boolean clearRoles(User user, Connection connection) {
        String query = "DELETE FROM users_roles WHERE user_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, user.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't clear user roles", e);
        }
    }

    private Set<Role> getUserRoles(Long userId, Connection connection) {
        String query = "SELECT r.role_id, role_name FROM roles r INNER JOIN users_roles ur "
                + "ON ur.role_id = r.role_id WHERE ur.user_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            Set<Role> roles = new HashSet<>();
            while (resultSet.next()) {
                Long roleId = resultSet.getLong("role_id");
                String roleName = resultSet.getString("role_name");
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
