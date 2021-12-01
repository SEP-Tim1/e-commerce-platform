package sep.webshopback.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductQuantityDTO {

    private long productId;
    private String productName;
    private float productPrice;
    private int quantity;
    private float total;
}