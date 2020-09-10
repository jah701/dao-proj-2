package internet.shop.controller.prouct;

import internet.shop.lib.Injector;
import internet.shop.model.Product;
import internet.shop.service.ProductService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateProductController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("internet.shop");
    private static ProductService productService
            = (ProductService) injector.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/products/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String productName = req.getParameter("product-name");
        String productPrice = req.getParameter("product-price");
        Product product = new Product(productName, Double.valueOf(productPrice));
        productService.create(product);
        resp.sendRedirect(req.getContextPath() + "/products/all");
    }
}
