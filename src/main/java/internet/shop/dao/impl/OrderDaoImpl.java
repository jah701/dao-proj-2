package internet.shop.dao.impl;

import internet.shop.dao.OrderDao;
import internet.shop.db.Storage;
import internet.shop.lib.Dao;
import internet.shop.model.Order;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Dao
public class OrderDaoImpl implements OrderDao {
    @Override
    public Order create(Order order) {
        Storage.addOrder(order);
        return order;
    }

    @Override
    public Optional<Order> get(Long id) {
        return Storage.orders.stream()
                .filter(x -> x.getId().equals(id)).findFirst();
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return Storage.orders.stream()
                .filter(x -> x.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> getAll() {
        return Storage.orders;
    }

    @Override
    public Order update(Order order) {
        for (int i = 0; i < Storage.orders.size(); i++) {
            if (Storage.orders.get(i).getId().equals(order.getId())) {
                Storage.orders.set(i, order);
            }
        }
        return order;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.orders.remove(get(id - 1).orElseThrow());
    }
}
