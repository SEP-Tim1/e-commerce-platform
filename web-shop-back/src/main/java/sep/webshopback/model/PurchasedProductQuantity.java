package sep.webshopback.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PurchasedProductQuantity {

    @Embedded
    private ProductSnapshot product;
    @Column(name = "quantity")
    private int quantity;

    public float getTotal() {
        return quantity * product.getPrice();
    }
}
