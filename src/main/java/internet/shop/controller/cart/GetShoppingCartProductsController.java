package internet.shop.controller.cart;

import internet.shop.controller.user.LoginController;
import internet.shop.lib.Injector;
import internet.shop.model.Product;
import internet.shop.service.ShoppingCartService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetShoppingCartProductsController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("internet.shop");
    private ShoppingCartService shoppingCartService
            = (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession().getAttribute(LoginController.USER_ID);
        List<Product> productList = shoppingCartService.getByUserId(userId).getProducts();
        req.setAttribute("productsInCart", productList);
        req.getRequestDispatcher("/WEB-INF/views/carts/cart.jsp").forward(req, resp);
    }
}
