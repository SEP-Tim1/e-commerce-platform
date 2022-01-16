package sep.webshopback.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sep.webshopback.exceptions.CartInvalidException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private User user;
    @ManyToOne
    private Store store;
    @ElementCollection
    private List<ProductQuantity> products;
    @Column
    private boolean active;
    @Column
    private LocalDateTime inactivated;

    public ShoppingCart(User user, Store store, List<ProductQuantity> products) {
        this.user = user;
        this.store = store;
        this.products = products;
        this.active = true;
    }

    public void appendProduct(Product product) {
        if (!active) { return; }
        if (products.stream().anyMatch(p -> p.getProduct().getId() == product.getId())) { return; }
        products.add(new ProductQuantity(product, 1));
    }

    public void removeProductById(long productId) {
        if (!active) { return; }
        products.removeIf(p -> p.getProduct().getId() == productId);
    }

    public void changeQuantityByProductId(long productId, int newQuantity) {
        if (!active) { return; }
        if (newQuantity <= 0) { return; }
        for (ProductQuantity p : products) {
            if (p.getProduct().getId() == productId) {
                p.setQuantity(newQuantity);
            }
        }
    }

    public float getTotal() {
        float sum = 0;
        LocalDateTime timePoint = active ? LocalDateTime.now() : inactivated;
        for (ProductQuantity p : products) {
            sum += p.getTotal(timePoint);
        }
        return sum;
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }

    public void setActive(boolean active) {
        this.active = active;
        if (!active) {
            inactivated = LocalDateTime.now();
        }
    }

    public void validate() throws CartInvalidException {
        if (!products.stream().allMatch(p -> p.getProduct().getCurrentBillingCycle() == BillingCycle.ONE_TIME)
            || !products.stream().allMatch(p -> p.getProduct().getCurrentBillingCycle() != BillingCycle.ONE_TIME)) {
            throw new CartInvalidException("All items must be either one-time or subscription based payments");
        }
        if (products.stream().allMatch(p -> p.getProduct().getCurrentBillingCycle() != BillingCycle.ONE_TIME) && products.size() > 1) {
            throw new CartInvalidException("Only one subscription based product can be in a cart");
        }
    }
}
