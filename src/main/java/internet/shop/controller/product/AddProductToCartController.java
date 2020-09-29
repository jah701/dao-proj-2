package internet.shop.controller.product;

import internet.shop.controller.user.LoginController;
import internet.shop.lib.Injector;
import internet.shop.model.Product;
import internet.shop.model.ShoppingCart;
import internet.shop.service.ProductService;
import internet.shop.service.ShoppingCartService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddProductToCartController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("internet.shop");
    private ProductService productService
            = (ProductService) injector.getInstance(ProductService.class);
    private ShoppingCartService shoppingCartService
            = (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        Long productId = Long.valueOf(id);
        Long userId = (Long) req.getSession().getAttribute(LoginController.USER_ID);
        Product product = productService.get(productId);
        ShoppingCart cart = shoppingCartService.getByUserId(userId);
        shoppingCartService.addProduct(cart, product);
        resp.sendRedirect(req.getContextPath() + "/shopping-cart/products");
    }
}
