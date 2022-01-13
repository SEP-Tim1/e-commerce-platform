package sep.webshopback.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private ShoppingCart cart;
    @Embedded
    private PurchaseOutcome outcome;

    public Purchase(PurchaseUserDetails userDetails, LocalDateTime created, ShoppingCart cart) {
        this.userDetails = userDetails;
        this.created = created;
        this.cart = cart;
        cart.setActive(false);
    }

    public float getTotal() {
        return cart.getTotal();
    }

    public void setOutcome(PurchaseOutcome outcome) {
        this.outcome = outcome;
        if (outcome.getStatus() == PurchaseStatus.SUCCESS) {
            cart.setActive(false);
        }
        else {
            cart.setActive(true);
        }
    }
}
