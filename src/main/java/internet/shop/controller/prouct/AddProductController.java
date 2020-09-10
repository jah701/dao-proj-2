package internet.shop.controller.prouct;

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

public class AddProductController extends HttpServlet {
    private static final Long USER_ID = 1L;
    private static final Injector injector = Injector.getInstance("internet.shop");
    private static ProductService productService
            = (ProductService) injector.getInstance(ProductService.class);
    private static ShoppingCartService shoppingCartService
            = (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        Long longId = Long.valueOf(id);
        Product product = productService.get(longId);
        ShoppingCart cart = shoppingCartService.getByUserId(USER_ID);
        cart.addProduct(product);
        resp.sendRedirect(req.getContextPath() + "/cart/products");
    }
}