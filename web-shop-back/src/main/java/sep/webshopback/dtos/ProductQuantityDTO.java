package sep.webshopback.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sep.webshopback.model.BillingCycle;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductQuantityDTO {

    private long productId;
    private String productName;
    private float productPrice;
    private BillingCycle billingCycle;
    private boolean hasQuantity;
    private int quantity;
    private float total;
}
