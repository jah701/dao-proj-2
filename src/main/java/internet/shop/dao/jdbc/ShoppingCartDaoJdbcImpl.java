package internet.shop.dao.jdbc;

import internet.shop.dao.ShoppingCartDao;
import internet.shop.exceptions.DataProcessingException;
import internet.shop.lib.Dao;
import internet.shop.model.Product;
import internet.shop.model.ShoppingCart;
import internet.shop.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class ShoppingCartDaoJdbcImpl implements ShoppingCartDao {
    @Override
    public Optional<ShoppingCart> getByUserId(Long id) {
        String query = "SELECT * FROM shopping_carts WHERE user_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
                PreparedStatement statement
                        = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ShoppingCart shoppingCart = getCartFromResultSet(resultSet);
                shoppingCart.addProduct(getProductsFromCart(shoppingCart.getId(), connection));
                return Optional.of(shoppingCart);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get shopping cart with user id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public ShoppingCart create(ShoppingCart cart) {
        String query = "INSERT INTO shopping_carts(shopping_cart_id,user_id) VALUES (?, ?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
                PreparedStatement statement
                        = connection.prepareStatement(query);
            statement.setLong(1, cart.getId());
            statement.setLong(2, cart.getUserId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create shopping cart", e);
        }
        return cart;
    }

    @Override
    public Optional<ShoppingCart> get(Long id) {
        String query = "SELECT * FROM shopping_carts WHERE shopping_cart_id = ?;";
        ShoppingCart cart;
        try (Connection connection = ConnectionUtil.getConnection()) {
                PreparedStatement statement
                        = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                cart = getCartFromResultSet(resultSet);
                cart.addProduct(getProductsFromCart(cart.getId(), connection));
                return Optional.of(cart);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get shopping cart with id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<ShoppingCart> getAll() {
        String query = "SELECT * FROM shopping_carts;";
        try (Connection connection = ConnectionUtil.getConnection()) {
                PreparedStatement statement
                        = connection.prepareStatement(query);
            List<ShoppingCart> carts = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ShoppingCart shoppingCart = getCartFromResultSet(resultSet);
                shoppingCart.addProduct(getProductsFromCart(shoppingCart.getId(), connection));
                carts.add(shoppingCart);
            }
            return carts;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get list with shopping carts", e);
        }
    }

    @Override
    public ShoppingCart update(ShoppingCart cart) {
        String query = "DELETE FROM shopping_carts_products WHERE shopping_cart_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
                PreparedStatement statement
                        = connection.prepareStatement(query);
            statement.setLong(1, cart.getId());
            statement.executeUpdate();
            addProductsToCart(cart, connection);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update shopping cart", e);
        }
        return cart;
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM shopping_carts WHERE shopping_cart_id = ?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
                PreparedStatement statement
                        = connection.prepareStatement(query);
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete shopping cart with id: " + id, e);
        }
    }

    private ShoppingCart getCartFromResultSet(ResultSet resultSet) throws SQLException {
        Long cartId = resultSet.getLong("shopping_cart_id");
        Long userId = resultSet.getLong("user_id");
        return new ShoppingCart(cartId, userId);
    }

    private ShoppingCart addProductsToCart(ShoppingCart shoppingCart, Connection connection) {
        String query = "INSERT INTO shopping_carts_products(shopping_cart_id, product_id) "
                + "VALUES (?, ?);";
        try (PreparedStatement statement
                    = connection.prepareStatement(query)) {
            for (Product product : shoppingCart.getProducts()) {
                statement.setLong(1, shoppingCart.getId());
                statement.setLong(2, product.getId());
                statement.executeUpdate();
            }
            return shoppingCart;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add products to shopping cart", e);
        }
    }

    private List<Product> getProductsFromCart(Long cartId, Connection connection) {
        String query = "SELECT * FROM products p INNER JOIN shopping_carts_products scp "
                + "ON p.product_id = scp.product_id WHERE scp.shopping_cart_id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, cartId);
            ResultSet resultSet = statement.executeQuery();
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                Long productId = resultSet.getLong("product_id");
                String name = resultSet.getString("product_name");
                Double price = resultSet.getDouble("product_price");
                products.add(new Product(productId, name, price));
            }
            return products;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get shopping cart products with ID = "
                    + cartId, e);
        }
    }
}

