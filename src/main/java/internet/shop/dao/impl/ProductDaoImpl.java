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
    public Product createProduct(Product product) {
        return product;
    }

    @Override
    public void addProduct(Product product) {
        Storage.addProduct(product);
    }

    @Override
    public Optional<Product> getProduct(Long id) {
        return Storage.products.stream()
                .filter(x -> x.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean removeProduct(Long id) {
        return Storage.products.remove(getProduct(id).orElseThrow());
    }

    @Override
    public List<Product> getAllProducts() {
        return Storage.products;
    }

    @Override
    public void updateProduct(Product product) {
        for (int i = 0; i < Storage.products.size(); i++) {
            if (product.getId().equals(Storage.products.get(i).getId())) {
                Storage.products.set(i, product);
                return;
            }
        }
    }
}
