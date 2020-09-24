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
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement
                    = connection.prepareStatement(
                    "SELECT * FROM shopping_carts WHERE user_id = ?;");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ShoppingCart shoppingCart = getCartFromResultSet(resultSet);
                for (Product product : getProductsFromCart(shoppingCart.getId())) {
                    shoppingCart.addProduct(product);
                }
                return Optional.of(shoppingCart);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get shopping cart with user id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public ShoppingCart create(ShoppingCart cart) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement
                    = connection.prepareStatement(
                    "INSERT INTO shopping_carts(shopping_cart_id,user_id) VALUES (?, ?);");
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
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement
                    = connection.prepareStatement(
                    "SELECT * FROM shopping_carts WHERE shopping_cart_id = ?;");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ShoppingCart cart = getCartFromResultSet(resultSet);
                return Optional.of(cart);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get shopping cart with id: " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<ShoppingCart> getAll() {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement
                    = connection.prepareStatement("SELECT * FROM shopping_carts;");
            List<ShoppingCart> carts = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ShoppingCart shoppingCart = getCartFromResultSet(resultSet);
                carts.add(shoppingCart);
            }
            return carts;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get list with shopping carts", e);
        }
    }

    @Override
    public ShoppingCart update(ShoppingCart cart) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement
                    = connection.prepareStatement(
                    "DELETE FROM shopping_carts_products WHERE shopping_cart_id = ?;");
            statement.setLong(1, cart.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update shopping cart", e);
        }
        addProductsToCart(cart);
        return cart;
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement
                    = connection.prepareStatement(
                    "DELETE FROM shopping_carts WHERE shopping_cart_id = ?;");
            statement.setLong(1, id);
            return 1 == statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete shopping cart with id: " + id, e);
        }
    }

    private ShoppingCart getCartFromResultSet(ResultSet resultSet) throws SQLException {
        Long cartId = resultSet.getLong("shopping_cart_id");
        Long userId = resultSet.getLong("user_id");
        return new ShoppingCart(cartId, userId);
    }

    private ShoppingCart addProductsToCart(ShoppingCart shoppingCart) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement
                    = connection.prepareStatement(
                    "INSERT INTO shopping_carts_products(shopping_cart_id, product_id) "
                            + "VALUES (?, ?);");
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

    private List<Product> getProductsFromCart(Long cartId) {
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT * FROM products p INNER JOIN shopping_carts_products scp "
                            + "ON p.product_id = scp.product_id WHERE scp.shopping_cart_id = ?;")) {
            statement.setLong(1, cartId);
            ResultSet resultSet = statement.executeQuery();
            List<Product> products = new ArrayList<>();
            while (resultSet.next()) {
                Long productId = resultSet.getLong("product_id");
                String name = resultSet.getString("productName");
                Double price = resultSet.getDouble("productPrice");
                products.add(new Product(productId, name, price));
            }
            return products;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't get shopping cart products with ID = "
                    + cartId, e);
        }
    }
}

