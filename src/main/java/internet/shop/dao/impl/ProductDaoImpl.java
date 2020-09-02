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
    public Product createProduct(String name, Double price) {
        return new Product(name, price);
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
        Storage.products.stream()
                .filter(x -> x.getName().equals(product.getName()))
                .forEach(x -> x.setPrice(product.getPrice()));
    }
}
