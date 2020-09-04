package internet.shop.service;

import internet.shop.model.Product;
import java.util.List;

public interface ProductService {
    Product createProduct(Product product);

    void addProduct(Product product);

    Product getProduct(Long id);

    boolean removeProduct(Long id);

    List<Product> getAllProducts();

    void updateProduct(Product product);
}
