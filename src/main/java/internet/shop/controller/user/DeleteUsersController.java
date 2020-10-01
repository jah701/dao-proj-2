package internet.shop.controller.user;

import internet.shop.lib.Injector;
import internet.shop.service.ShoppingCartService;
import internet.shop.service.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteUsersController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("internet.shop");
    private UserService userService
            = (UserService) injector.getInstance(UserService.class);
    private ShoppingCartService shoppingCartService
            = (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        Long userId = Long.valueOf(id);
        userService.delete(userId);
        shoppingCartService.delete(userId);
        resp.sendRedirect(req.getContextPath() + "/users/all");
    }
}
