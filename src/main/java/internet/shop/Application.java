package internet.shop;

import internet.shop.dao.jdbc.ProductDaoJdbcImpl;
import internet.shop.model.Product;

public class Application {

    public static void main(String[] args) {
        ProductDaoJdbcImpl productDaoJdbc = new ProductDaoJdbcImpl();
        Product product = new Product(1L, "Product", 10.0);
        System.out.println(productDaoJdbc.create(product));

        System.out.println(productDaoJdbc.get(1L));

        product.setPrice(20.00);

        System.out.println(productDaoJdbc.getAll());

        System.out.println(productDaoJdbc.update(product));

        productDaoJdbc.delete(1L);
    }
}
