package internet.shop.dao.jdbc;

import internet.shop.dao.ProductDao;
import internet.shop.exceptions.DataProcessingException;
import internet.shop.lib.Dao;
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
public class ProductDaoJdbcImpl implements ProductDao {
    @Override
    public Product create(Product product) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO products(name, price) VALUES (?, ?);",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                product.setId(rs.getLong(1));
            }
            return product;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create product", e);
        }
    }

    @Override
    public Optional<Product> get(Long id) {
        Product product;
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM products WHERE product_id = ?;");
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                product = getProductFromResultSet(rs);
                return Optional.of(product);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get the product with id = " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM products WHERE deleted = false;");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Product product = getProductFromResultSet(rs);
                products.add(product);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all products list", e);
        }
        return products;
    }

    @Override
    public Product update(Product product) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE products SET name = ?, price = ? WHERE product_id = ?;");
            statement.setString(1, product.getName());
            statement.setDouble(2, product.getPrice());
            statement.setLong(3, product.getId());
            statement.executeUpdate();
            return product;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update product", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE products SET deleted = true WHERE product_id = ? AND deleted = false");
            statement.setLong(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DataProcessingException("Couldn't delete product with ID = " + id, e);
        }
    }

    private Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
        Long productId = resultSet.getLong("product_id");
        String productName = resultSet.getString("name");
        Double productPrice = resultSet.getDouble("price");
        return new Product(productId, productName, productPrice);
    }
}
