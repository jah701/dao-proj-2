package internet.shop.factory;

import internet.shop.dao.ProductDao;
import internet.shop.dao.impl.ProductDaoImpl;

public class Factory {
    private static ProductDao productDao;

    public static ProductDao getProductDao() {
        if (productDao == null) {
            return new ProductDaoImpl();
        }
        return productDao;
    }
}
