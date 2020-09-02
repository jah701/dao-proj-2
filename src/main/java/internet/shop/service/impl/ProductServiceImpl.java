package internet.shop.service.impl;

import internet.shop.dao.ProductDao;
import internet.shop.lib.Inject;
import internet.shop.lib.Service;
import internet.shop.model.Product;
import internet.shop.service.ProductService;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Inject
    private ProductDao productDao;

    @Override
    public Product createProduct(String name, Double price) {
        return productDao.createProduct(name, price);
    }

    @Override
    public void addProduct(Product product) {
        productDao.addProduct(product);
    }

    @Override
    public Optional<Product> getProduct(Long id) {
        return productDao.getProduct(id);
    }

    @Override
    public boolean removeProduct(Long id) {
        return productDao.removeProduct(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    @Override
    public void updateProduct(Product product) {
        productDao.updateProduct(product);
    }
}

