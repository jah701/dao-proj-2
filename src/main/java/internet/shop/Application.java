package internet.shop;

import internet.shop.dao.ProductDao;
import internet.shop.dao.jdbc.ProductDaoJdbcImpl;
import internet.shop.lib.Injector;
import internet.shop.model.Product;

public class Application {

    public static void main(String[] args) {
        Injector injector = Injector.getInstance("internet.shop");
        ProductDao productDao = new ProductDaoJdbcImpl();
        Product product = new Product(1L, "Product", 10.0);
        System.out.println(productDao.create(product));

        System.out.println(productDao.get(1L));

        product.setPrice(20.00);

        System.out.println(productDao.getAll());

        System.out.println(productDao.update(product));

        System.out.println(productDao.delete(15L));
    }
}
