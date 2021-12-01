package sep.webshopback.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sep.webshopback.model.Product;
import sep.webshopback.model.Store;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StoreDTO {
    private long id;

    private String name;

    private List<Product> products = new ArrayList<>();

    private long owner;

    public StoreDTO(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.products = store.getProducts();
        this.owner = store.getOwner().getId();
    }
}
