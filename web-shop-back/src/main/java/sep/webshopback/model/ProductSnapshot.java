package sep.webshopback.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductSnapshot {

    @Column(length = 50, name = "name")
    @NotBlank
    private String name;

    @Column(name = "price")
    @NotNull
    private float price;
}
