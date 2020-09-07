package internet.shop.service.impl;

import internet.shop.dao.ShoppingCartDao;
import internet.shop.lib.Inject;
import internet.shop.lib.Service;
import internet.shop.model.Product;
import internet.shop.model.ShoppingCart;
import internet.shop.service.ShoppingCartService;
import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Inject
    private ShoppingCartDao shoppingCartDao;

    public List<Product> getProducts(ShoppingCart shoppingCart) {
        return shoppingCartDao.getProducts(shoppingCart);
    }

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        return shoppingCartDao.create(shoppingCart);
    }

    @Override
    public ShoppingCart addProduct(ShoppingCart shoppingCart, Product product) {
        shoppingCart.getProducts().add(product);
        return shoppingCart;
    }

    @Override
    public boolean deleteProduct(ShoppingCart shoppingCart, Product product) {
        shoppingCart.deleteProduct(product);
        return shoppingCartDao.update(shoppingCart);
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        shoppingCart.clearProducts();
    }

    @Override
    public ShoppingCart getByUserId(Long userId) {
        return shoppingCartDao.get(userId).get();
    }

    @Override
    public boolean delete(ShoppingCart shoppingCart) {
        return shoppingCartDao.delete(shoppingCart.getId());
    }
}
