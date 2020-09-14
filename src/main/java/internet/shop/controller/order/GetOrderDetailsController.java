package internet.shop.controller.order;

import internet.shop.lib.Injector;
import internet.shop.model.Order;
import internet.shop.service.OrderService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetOrderDetailsController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("internet.shop");
    private OrderService orderService = (OrderService) injector.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        Order order = orderService.get(id);
        req.setAttribute("order", order);
        req.getRequestDispatcher("/WEB-INF/views/orders/order-details.jsp").forward(req, resp);
    }
}
