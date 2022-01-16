package sep.webshopback.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sep.webshopback.model.BillingCycle;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDTO {
	
	private String name;
	private BillingCycle billingCycle;
	private String price;
	private boolean hasQuantity;
	private long quantity;
}
