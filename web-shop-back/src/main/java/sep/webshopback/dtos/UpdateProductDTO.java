package sep.webshopback.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateProductDTO {

    private long id;
    private String name;
    private float price;
    private long quantity;
    private String imageUrl;
}
