package sep.webshopback.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Embedded
    private PurchaseUserDetails userDetails;
    @Column(name="created")
    private LocalDateTime created;
    @ManyToOne
    private Store store;
    @ElementCollection
    private List<PurchasedProductQuantity> products;
    @ManyToOne
    private ShoppingCart cart;

    public Purchase(PurchaseUserDetails userDetails, LocalDateTime created, Store store, ShoppingCart cart) {
        this.userDetails = userDetails;
        this.created = created;
        this.store = store;
        this.cart = cart;
        this.products = new ArrayList<>();
    }

    public float getTotal() {
        float sum = 0;
        for (PurchasedProductQuantity p : products) {
            sum += p.getTotal();
        }
        return sum;
    }

    public void addProduct(ProductQuantity product) {
        if (products == null) {
            products = new ArrayList<>();
        }
        PurchasedProductQuantity purchased = new PurchasedProductQuantity(
            new ProductSnapshot(
                    product.getProduct().getName(),
                    product.getProduct().getPrice()),
                product.getQuantity()
        );
        products.add(purchased);
    }
}
