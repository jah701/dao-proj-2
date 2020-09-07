package internet.shop.factory;

import internet.shop.dao.OrderDao;
import internet.shop.dao.ProductDao;
import internet.shop.dao.impl.OrderDaoImpl;
import internet.shop.dao.impl.ProductDaoImpl;

public class Factory {
    private static ProductDao productDao;
    private static OrderDao orderDao;

    public static ProductDao getProductDao() {
        if (productDao == null) {
            return new ProductDaoImpl();
        }
        return productDao;
    }

    public static OrderDao getOrderDao() {
        if (orderDao == null) {
            return new OrderDaoImpl();
        }
        return orderDao;
    }
}
