package internet.shop.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShoppingCart {
    private Long id;
    private List<Product> products;
    private Long userId;

    public ShoppingCart() {
    }

    public ShoppingCart(Long userId) {
        this.userId = userId;
        this.id = userId;
        this.products = new ArrayList<>();
    }

    public ShoppingCart(Long id, Long userId) {
        this.id = id;
        this.userId = userId;
        this.products = new ArrayList<>();
    }

    public ShoppingCart(Long id, List<Product> products, Long userId) {
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
}
