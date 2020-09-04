package internet.shop;

import internet.shop.dao.impl.ProductDaoImpl;
import internet.shop.lib.Injector;
import internet.shop.model.Product;
import internet.shop.service.ProductService;

public class Application {
    private static Injector injector = Injector.getInstance("internet.shop");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);

        Product iphone = new ProductDaoImpl().createProduct(new Product("iPhone", 200.00));
        productService.addProduct(iphone);

        System.out.println(productService.getProduct(1L));

        boolean isRemoved = productService.removeProduct(1L);
        System.out.println("Product was removed: " + isRemoved);

        Product samsung = new ProductDaoImpl().createProduct(new Product("Samsung", 100.00));
        Product xiaomi = new ProductDaoImpl().createProduct(new Product("Xiaomi", 50.00));

        productService.addProduct(samsung);
        productService.addProduct(xiaomi);

        Product samsung2 = new ProductDaoImpl().createProduct(new Product("Samsung", 500.00));
        samsung2.setId(2L);
        productService.updateProduct(samsung2);

        System.out.println("List of all products: " + productService.getAllProducts());
    }
}
