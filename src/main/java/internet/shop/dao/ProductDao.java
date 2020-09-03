package internet.shop.dao;

import internet.shop.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductDao {
    Product createProduct(Product product);

    void addProduct(Product product);

    Optional<Product> getProduct(Long id);

    boolean removeProduct(Long id);

    List<Product> getAllProducts();

    void updateProduct(Product product);
}
