package sep.webshopback.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Embeddable
public class ProductQuantity {

    @ManyToOne
    private Product product;
    @Column(name="quantity")
    private int quantity;

    public ProductQuantity(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    protected ProductQuantity() {

    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getTotal(LocalDateTime timePoint) {
        return quantity * product.getPrice(timePoint);
    }
}
