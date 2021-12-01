package sep.webshopback.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductSnapshot {

    @Column(name = "productId")
    private long productId;
    @Column(name = "price")
    private float price;
}
