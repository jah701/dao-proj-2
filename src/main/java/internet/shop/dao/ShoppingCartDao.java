package internet.shop.dao;

import internet.shop.model.Product;
import internet.shop.model.ShoppingCart;
import java.util.List;
import java.util.Optional;

public interface ShoppingCartDao {
    ShoppingCart create(ShoppingCart shoppingCart);

    Optional<ShoppingCart> get(Long id);

    List<ShoppingCart> getAll();

    boolean update(ShoppingCart shoppingCart);

    List<Product> getProducts(ShoppingCart shoppingCart);

    boolean delete(Long id);
}
