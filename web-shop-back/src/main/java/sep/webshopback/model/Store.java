package sep.webshopback.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sep.webshopback.exceptions.ProductNotFoundException;
import sep.webshopback.exceptions.ProductNotInStockException;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stores")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "store_generator")
    @SequenceGenerator(name="store_generator", sequenceName = "store_seq", allocationSize=50, initialValue = 10)
    private long id;

    @Column(length = 50, name = "name")
    @NotBlank
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User owner;

    @Column
    private String apiToken;

    public Store(String name, List<Product> products, User owner) {
        this.name = name;
        this.products = products;
        this.owner = owner;
    }

    public Store(String name, User owner) {
        this.name = name;
        this.products = new ArrayList<>();
        this.owner = owner;
    }

    public void decreaseQuantity(Product product, int quantity) throws ProductNotFoundException, ProductNotInStockException {
        Product prod = products.stream().filter(p -> p.getId() == product.getId())
                .findFirst().orElse(null);
        if (prod == null) {
            throw new ProductNotFoundException();
        }
        prod.decreaseQuantity(quantity);
    }
}
