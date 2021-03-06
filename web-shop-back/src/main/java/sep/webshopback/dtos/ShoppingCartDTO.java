package sep.webshopback.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShoppingCartDTO {

    private long id;
    private String storeName;
    private List<ProductQuantityDTO> products;
    private float total;
}
