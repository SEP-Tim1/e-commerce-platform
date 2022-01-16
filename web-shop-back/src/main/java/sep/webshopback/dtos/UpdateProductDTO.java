package sep.webshopback.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sep.webshopback.model.BillingCycle;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateProductDTO {

    private long id;
    private String name;
    private BillingCycle billingCycle;
    private float price;
    private boolean hasQuantity;
    private long quantity;
    private String imageUrl;
}
