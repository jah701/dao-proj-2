package internet.shop.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShoppingCart {
    private Long id;
    private List<Product> products;
    private Long userId;

    public ShoppingCart(Long userId) {
        this.userId = userId;
        this.products = new ArrayList<>();
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

    public void clearProducts() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product...productsArgs) {
        this.products.addAll(Arrays.asList(productsArgs));
    }

    public boolean deleteProduct(Product product) {
        return products.remove(product);
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "- SHOPPING CART INFO -\nid: " + id + "\nContent: " + products.toString()
                + "\nCustomer id: " + userId + "\n\n";
    }
}
