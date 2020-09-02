package internet.shop.factory;

import internet.shop.dao.OrderDao;
import internet.shop.dao.ProductDao;
import internet.shop.dao.ShoppingCartDao;
import internet.shop.dao.UserDao;
import internet.shop.dao.impl.OrderDaoImpl;
import internet.shop.dao.impl.ProductDaoImpl;
import internet.shop.dao.impl.ShoppingCartDaoImpl;
import internet.shop.dao.impl.UserDaoImpl;

public class Factory {
    private static OrderDao orderDao;
    private static ProductDao productDao;
    private static ShoppingCartDao shoppingCartDao;
    private static UserDao userDao;

    public static OrderDao getOrderDao() {
        if (orderDao == null) {
            return new OrderDaoImpl();
        }
        return orderDao;
    }

    public static ProductDao getProductDao() {
        if (productDao == null) {
            return new ProductDaoImpl();
        }
        return productDao;
    }

    public static ShoppingCartDao getShoppingCartDao() {
        if (shoppingCartDao == null) {
            return new ShoppingCartDaoImpl();
        }
        return shoppingCartDao;
    }

    public static UserDao getUserDao() {
        if (userDao == null) {
            return new UserDaoImpl();
        }
        return userDao;
    }
}
