package sep.webshopback.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewInfoDTO {

    @Size(max=50)
    private String firstName;

    @Size(max=50)
    private String lastName;

    @Size(max=20)
    private String phone;

    @Size(max=200)
    private String address;
}
