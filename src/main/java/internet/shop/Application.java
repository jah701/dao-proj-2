package internet.shop;

import internet.shop.dao.ProductDao;
import internet.shop.dao.ShoppingCartDao;
import internet.shop.dao.UserDao;
import internet.shop.lib.Injector;

public class Application {

    public static void main(String[] args) {
        Injector injector = Injector.getInstance("internet.shop");
        ProductDao productDao = (ProductDao) injector.getInstance(ProductDao.class);
        ShoppingCartDao shoppingCartDao
                = (ShoppingCartDao) injector.getInstance(ShoppingCartDao.class);

        UserDao userDao = (UserDao) injector.getInstance(UserDao.class);
    }
}
