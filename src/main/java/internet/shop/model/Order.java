package internet.shop.model;

import java.util.List;
import java.util.Objects;

public class Order {
    private Long id;
    private List<Product> products;
    private Long userId;

    public Order(Long id, Long userId) {
        this.id = id;
        this.userId = userId;
    }

    public Order() {
    }

    public Order(Long id, List<Product> products, Long userId) {
        this.id = id;
        this.products = products;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "- ORDER INFO -\nid: " + id + "\nContent: " + products
                + "\nCustomer id: " + userId + "\n\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(products, order.products)
                && Objects.equals(userId, order.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, products, userId);
    }
}
