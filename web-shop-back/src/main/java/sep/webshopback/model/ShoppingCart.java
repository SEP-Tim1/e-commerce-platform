package sep.webshopback.model;

import javax.persistence.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Entity
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

    public ShoppingCart(long id, User user, Store store, List<ProductQuantity> products) {
        this.id = id;
        this.user = user;
        this.store = store;
        this.products = products;
    }

    public ShoppingCart(User user, Store store, List<ProductQuantity> products) {
        this.user = user;
        this.store = store;
        this.products = products;
    }

    protected ShoppingCart() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public List<ProductQuantity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductQuantity> products) {
        this.products = products;
    }

    public void appendProduct(Product product) {
        if (products.stream().anyMatch(p -> p.getProduct().getId() == product.getId())) {
            return;
        }
        products.add(new ProductQuantity(product, 1));
    }

    public void removeProductById(long productId) {
        products.removeIf(p -> p.getProduct().getId() == productId);
    }

    public void changeQuantityByProductId(long productId, int newQuantity) {
        if (newQuantity <= 0) {
            return;
        }
        for (ProductQuantity p : products) {
            if (p.getProduct().getId() == productId) {
                p.setQuantity(newQuantity);
            }
        }
    }

    public float getTotal() {
        float sum = 0;
        for (ProductQuantity p : products) {
            sum += p.getTotal();
        }
        return sum;
    }

    public boolean isEmpty() {
        return products.isEmpty();
    }
}
