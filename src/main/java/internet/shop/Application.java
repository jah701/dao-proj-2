package internet.shop;

import internet.shop.lib.Injector;
import internet.shop.model.Product;
import internet.shop.model.ShoppingCart;
import internet.shop.model.User;
import internet.shop.service.OrderService;
import internet.shop.service.ProductService;
import internet.shop.service.ShoppingCartService;
import internet.shop.service.UserService;

public class Application {
    private static Injector injector = Injector.getInstance("internet.shop");

    public static void main(String[] args) {
        final UserService userService = (UserService) injector.getInstance(UserService.class);
        final ShoppingCartService shoppingCartService
                = (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
        final ProductService productService
                = (ProductService) injector.getInstance(ProductService.class);
        final OrderService orderService = (OrderService) injector.getInstance(OrderService.class);

        User admin1 = new User("admin1", "admin1", "admin");
        User admin2 = new User("admin2", "admin2", "admin");
        User admin3 = new User("admin3", "admin3", "admin");
        User admin4 = new User("admin4", "admin4", "admin");
        userService.create(admin1);
        userService.create(admin2);
        userService.create(admin3);
        userService.create(admin4);

        ShoppingCart shoppingCart1 = new ShoppingCart(admin1.getId());
        ShoppingCart shoppingCart2 = new ShoppingCart(admin2.getId());
        ShoppingCart shoppingCart3 = new ShoppingCart(admin3.getId());
        ShoppingCart shoppingCart4 = new ShoppingCart(admin4.getId());
        shoppingCartService.create(shoppingCart1);
        shoppingCartService.create(shoppingCart2);
        shoppingCartService.create(shoppingCart3);
        shoppingCartService.create(shoppingCart4);

        Product product1 = new Product("iPhone", 150.00);
        Product product2 = new Product("Samsung", 100.00);
        Product product3 = new Product("Xiaomi", 50.00);
        productService.create(product1);
        productService.create(product2);
        productService.create(product3);

        shoppingCart1.addProduct(product1);
        orderService.completeOrder(shoppingCart1);
        shoppingCart1.addProduct(product2);
        orderService.completeOrder(shoppingCart1);
        System.out.println(shoppingCart1);
        System.out.println(orderService.getAll());
        System.out.println(orderService.getUserOrders(shoppingCart1.getUserId()));

        shoppingCart2.addProduct(product2, product3);
        System.out.println(shoppingCart2);
        orderService.completeOrder(shoppingCart2);
        System.out.println(orderService.get(3L));

        shoppingCart4.addProduct(product1, product2, product3);
        System.out.println(shoppingCart4);
        shoppingCartService.deleteProduct(shoppingCart4, product3);
        System.out.println(shoppingCart4);
        orderService.completeOrder(shoppingCart4);
        System.out.println(orderService.get(shoppingCart4.getUserId()));
    }
}
