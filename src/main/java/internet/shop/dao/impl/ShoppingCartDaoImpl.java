package internet.shop.dao.impl;

import internet.shop.dao.ShoppingCartDao;
import internet.shop.db.Storage;
import internet.shop.lib.Dao;
import internet.shop.model.Product;
import internet.shop.model.ShoppingCart;
import java.util.List;
import java.util.Optional;

@Dao
public class ShoppingCartDaoImpl implements ShoppingCartDao {
    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        Storage.addCart(shoppingCart);
        return shoppingCart;
    }

    @Override
    public Optional<ShoppingCart> get(Long id) {
        return Storage.carts.stream()
                .filter(x -> x.getId().equals(id)).findFirst();
    }

    @Override
    public List<ShoppingCart> getAll() {
        return Storage.carts;
    }

    @Override
    public boolean update(ShoppingCart shoppingCart) {
        for (int i = 0; i < Storage.carts.size(); i++) {
            if (Storage.carts.get(i).getId().equals(shoppingCart.getId())) {
                Storage.carts.set(i, shoppingCart);
                return true;
            }
        }
        return false;
    }

    public List<Product> getProducts(ShoppingCart shoppingCart) {
        for (ShoppingCart item : Storage.carts) {
            if (shoppingCart.getId().equals(item.getId())) {
                return item.getProducts();
            }
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.carts.remove(get(id).orElseThrow());
    }
}
