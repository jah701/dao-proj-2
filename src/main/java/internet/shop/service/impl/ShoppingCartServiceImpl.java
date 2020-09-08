package internet.shop.service.impl;

import internet.shop.dao.ShoppingCartDao;
import internet.shop.lib.Inject;
import internet.shop.lib.Service;
import internet.shop.model.Product;
import internet.shop.model.ShoppingCart;
import internet.shop.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Inject
    private ShoppingCartDao shoppingCartDao;

    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        return shoppingCartDao.create(shoppingCart);
    }

    @Override
    public ShoppingCart addProduct(ShoppingCart shoppingCart, Product product) {
        shoppingCart.addProduct(product);
        return shoppingCartDao.update(shoppingCart);
    }

    @Override
    public boolean deleteProduct(ShoppingCart shoppingCart, Product product) {
        boolean res = shoppingCart.deleteProduct(product);
        shoppingCartDao.update(shoppingCart);
        return res;
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        shoppingCart.clearProducts();
    }

    @Override
    public ShoppingCart getByUserId(Long userId) {
        return shoppingCartDao.getByUserId(userId).get();
    }

    @Override
    public boolean delete(Long id) {
        return shoppingCartDao.delete(id);
    }
}
