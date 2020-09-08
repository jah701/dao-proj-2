package internet.shop.dao.impl;

import internet.shop.dao.ShoppingCartDao;
import internet.shop.db.Storage;
import internet.shop.lib.Dao;
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
                .filter(o -> o.getId().equals(id)).findFirst();
    }

    @Override
    public List<ShoppingCart> getAll() {
        return Storage.carts;
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        for (int i = 0; i < Storage.carts.size(); i++) {
            if (Storage.carts.get(i).getId().equals(shoppingCart.getId())) {
                Storage.carts.set(i, shoppingCart);
            }
        }
        return shoppingCart;
    }

    @Override
    public Optional<ShoppingCart> getByUserId(Long id) {
        return Storage.carts.stream().filter(o -> o.getUserId().equals(id)).findFirst();
    }

    @Override
    public boolean delete(Long id) {
        return Storage.carts.removeIf(cart -> cart.getId().equals(id));
    }
}
