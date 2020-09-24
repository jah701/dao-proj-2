package internet.shop.dao.jdbc;

import internet.shop.dao.OrderDao;
import internet.shop.exceptions.DataProcessingException;
import internet.shop.lib.Dao;
import internet.shop.model.Order;
import internet.shop.model.Product;
import internet.shop.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class OrderDaoJdbcImpl implements OrderDao {
    @Override
    public List<Order> getUserOrders(Long id) {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM orders WHERE user_id = ?;");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                orders.add(getOrderFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get orders with user id: " + id, e);
        }
        for (Order order : orders) {
            order.setProducts(getProductsFromOrder(order.getId()));
        }
        return orders;
    }

    @Override
    public Order create(Order order) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO orders(user_id) VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, order.getUserId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                order.setId(resultSet.getLong(1));
            }

            return addProducts(order);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create order", e);
        }
    }

    @Override
    public Optional<Order> get(Long id) {
        Order order = new Order();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM orders WHERE order_id = ?;");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                order = getOrderFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get order with id: " + id, e);
        }
        order.setProducts(getProductsFromOrder(order.getId()));
        return Optional.of(order);
    }

    @Override
    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM orders;");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                orders.add(getOrderFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all orders list", e);
        }
        for (Order order : orders) {
            order.setProducts(getProductsFromOrder(order.getId()));
        }
        return orders;
    }

    @Override
    public Order update(Order order) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM orders_products WHERE order_id = ?;");
            statement.setLong(1, order.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update order", e);
        }
        return addProducts(order);
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE orders SET deleted = true WHERE order_id = ?;");
            statement.setLong(1, id);
            return 1 == statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete order with id: " + id, e);
        }
    }

    private Order addProducts(Order order) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO orders_products(order_id, product_id) VALUES(?, ?);");
            statement.setLong(1, order.getId());
            for (int i = 0; i < order.getProducts().size(); i++) {
                statement.setLong(2, order.getProducts().get(i).getId());
                statement.executeUpdate();
            }
            return order;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add products to order with id:"
                    + order.getId(), e);
        }
    }

    private List<Product> getProductsFromOrder(Long orderId) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM products p INNER JOIN orders_products op "
                            + "ON p.product_id = op.product_id WHERE op.order_id = ?;");
            List<Product> products = new ArrayList<>();
            statement.setLong(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long productId = resultSet.getLong("product_id");
                String productName = resultSet.getString("productName");
                Double productPrice = resultSet.getDouble("productPrice");
                products.add(new Product(productId, productName, productPrice));
            }
            return products;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get product from order with id: "
                    + orderId, e);
        }
    }

    private Order getOrderFromResultSet(ResultSet rs) throws SQLException {
        Long orderId = rs.getLong("order_id");
        Long userId = rs.getLong("user_id");
        return new Order(orderId, userId);
    }
}
