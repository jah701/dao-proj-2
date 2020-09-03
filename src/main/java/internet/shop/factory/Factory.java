package internet.shop.factory;

import internet.shop.dao.ProductDao;
import internet.shop.dao.UserDao;
import internet.shop.dao.impl.ProductDaoImpl;
import internet.shop.dao.impl.UserDaoImpl;

public class Factory {
    private static ProductDao productDao;
    private static UserDao userDao;

    public static ProductDao getProductDao() {
        if (productDao == null) {
            return new ProductDaoImpl();
        }
        return productDao;
    }

    public static UserDao getUserDao() {
        if (userDao == null) {
            return new UserDaoImpl();
        }
        return userDao;
    }
}
