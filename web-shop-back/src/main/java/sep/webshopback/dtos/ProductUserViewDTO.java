package sep.webshopback.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductUserViewDTO {

    private long id;
    private String name;
    private float price;
    private String imageUrl;
    private boolean available;
}
