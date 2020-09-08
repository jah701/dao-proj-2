package internet.shop.dao.impl;

import internet.shop.dao.ProductDao;
import internet.shop.db.Storage;
import internet.shop.lib.Dao;
import internet.shop.model.Product;
import java.util.List;
import java.util.Optional;

@Dao
public class ProductDaoImpl implements ProductDao {
    @Override
    public Product create(Product product) {
        Storage.addProduct(product);
        return product;
    }

    @Override
    public Optional<Product> get(Long id) {
        return Storage.products.stream()
                .filter(o -> o.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean remove(Long id) {
        return Storage.products.remove(get(id).orElseThrow());
    }

    @Override
    public List<Product> getAll() {
        return Storage.products;
    }

    @Override
    public void update(Product product) {
        for (int i = 0; i < Storage.products.size(); i++) {
            if (product.getId().equals(Storage.products.get(i).getId())) {
                Storage.products.set(i, product);
                return;
            }
        }
    }
}
