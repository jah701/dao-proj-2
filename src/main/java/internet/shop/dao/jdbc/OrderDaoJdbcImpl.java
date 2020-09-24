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
        Order order;
        String query = "SELECT * FROM orders WHERE deleted = false AND user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                order = getOrderFromResultSet(resultSet);
                order.setProducts(getProductsFromOrder(order.getId(), connection));
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get orders with user id: " + id, e);
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
            clearShoppingCart(order.getUserId(), connection);
            return addProductsToOrder(order, connection);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create order", e);
        }
    }

    @Override
    public Optional<Order> get(Long id) {
        Order order = null;
        try (Connection connection = ConnectionUtil.getConnection()) {
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT * FROM orders WHERE deleted = false AND order_id = ?;");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                order = getOrderFromResultSet(resultSet);
            }
            order.setProducts(getProductsFromOrder(order.getId(), connection));
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get order with id: " + id, e);
        }
        return Optional.of(order);
    }

    @Override
    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();
        Order order;
        try (Connection connection = ConnectionUtil.getConnection()) {
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT * FROM orders WHERE deleted = false;");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                order = getOrderFromResultSet(resultSet);
                order.setProducts(getProductsFromOrder(order.getId(), connection));
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all orders list", e);
        }
        return orders;
    }

    @Override
    public Order update(Order order) {
        String query = "DELETE FROM orders_products WHERE order_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
                PreparedStatement statement = connection.prepareStatement(
                        query);
            statement.setLong(1, order.getId());
            statement.executeUpdate();
            return addProductsToOrder(order, connection);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update order", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE orders SET deleted = true WHERE order_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
                PreparedStatement statement = connection.prepareStatement(
                        query);
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete order with id: " + id, e);
        }
    }

    private Order addProductsToOrder(Order order, Connection connection) {
        String query = "INSERT INTO orders_products(order_id, product_id) VALUES(?, ?);";
        try (PreparedStatement statement = connection.prepareStatement(
                    query)) {
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

    private List<Product> getProductsFromOrder(Long orderId, Connection connection) {
        String query = "SELECT * FROM products p INNER JOIN orders_products op "
                + "ON p.product_id = op.product_id WHERE op.order_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            List<Product> products = new ArrayList<>();
            statement.setLong(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long productId = resultSet.getLong("product_id");
                String productName = resultSet.getString("product_name");
                Double productPrice = resultSet.getDouble("product_price");
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

    private void clearShoppingCart(Long userId, Connection connection) {
        String query = "DELETE FROM shopping_carts_products WHERE shopping_cart_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't clear shopping cart for user with id:" + userId, e);
        }
    }
}
