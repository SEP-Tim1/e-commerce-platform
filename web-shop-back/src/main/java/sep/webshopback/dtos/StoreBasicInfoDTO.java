package sep.webshopback.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StoreBasicInfoDTO {

    private long id;
    private String name;
    private String email;
    private String phoneNumber;
    private List<ProductUserViewDTO> products;

    public StoreBasicInfoDTO(long id, String name, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
