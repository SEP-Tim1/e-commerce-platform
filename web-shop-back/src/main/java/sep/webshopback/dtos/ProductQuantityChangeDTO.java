package sep.webshopback.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductQuantityChangeDTO {

    private long cartId;
    private long productId;
    private int newQuantity;
}
