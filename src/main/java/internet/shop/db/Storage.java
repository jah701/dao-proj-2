package internet.shop.db;

import internet.shop.model.Order;
import internet.shop.model.Product;
import internet.shop.model.ShoppingCart;
import internet.shop.model.User;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    public static final List<Product> products = new ArrayList<>();
    public static final List<Order> orders = new ArrayList<>();
    public static final List<ShoppingCart> shoppingCarts = new ArrayList<>();
    public static final List<User> users = new ArrayList<>();
    private static Long productId = 0L;
    private static Long orderId = 0L;
    private static Long cartId = 0L;
    private static Long userId = 0L;

    public static void addProduct(Product product) {
        product.setId(++productId);
        products.add(product);
    }

    public static void addOrder(Order order) {
        order.setId(++orderId);
        orders.add(order);
    }

    public static void addCart(ShoppingCart shoppingCart) {
        shoppingCart.setId(++cartId);
        shoppingCarts.add(shoppingCart);
    }

    public static void addUser(User user) {
        user.setId(++userId);
        users.add(user);
    }
}
