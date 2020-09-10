package internet.shop.service;

import internet.shop.model.Product;
import java.util.List;

public interface ProductService {
    Product create(Product product);

    Product get(Long id);

    boolean delete(Long id);

    List<Product> getAll();

    void update(Product product);
}
