package internet.shop.controller;

import internet.shop.lib.Injector;
import internet.shop.model.ShoppingCart;
import internet.shop.model.User;
import internet.shop.service.ShoppingCartService;
import internet.shop.service.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InjectDataController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("internet.shop");
    private UserService userService = (UserService) injector.getInstance(UserService.class);
    private ShoppingCartService shoppingCartService
            = (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User bob = userService.create(new User("bob"));
        ShoppingCart shoppingCart1 = new ShoppingCart(bob.getId());
        shoppingCartService.create(shoppingCart1);
        req.getRequestDispatcher("/WEB-INF/views/injectData.jsp").forward(req, resp);
    }
}
