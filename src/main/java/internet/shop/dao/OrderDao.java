package internet.shop.dao;

import internet.shop.model.Order;
import java.util.List;

public interface OrderDao extends GenericDao<Order, Long> {
    List<Order> getUserOrders(Long id);
}
