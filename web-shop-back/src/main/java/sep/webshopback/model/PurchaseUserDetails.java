package sep.webshopback.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sep.webshopback.util.SensitiveDataConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import javax.validation.constraints.Email;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PurchaseUserDetails {

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "surname", nullable = false)
    private String surname;
    @Column(name = "address", nullable = false)
    @Convert(converter = SensitiveDataConverter.class)
    private String address;
    @Column(name = "phoneNumber", nullable = false)
    @Convert(converter = SensitiveDataConverter.class)
    private String phoneNumber;
    @Column(name = "email")
    @Email
    @Convert(converter = SensitiveDataConverter.class)
    private String email;
}
