package sep.webshopback.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sep.webshopback.model.BillingCycle;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductUserViewDTO {

    private long id;
    private String name;
    private float price;
    private BillingCycle billingCycle;
    private String imageUrl;
    private boolean available;
}
