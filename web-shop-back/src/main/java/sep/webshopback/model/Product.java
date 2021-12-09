package sep.webshopback.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sep.webshopback.exceptions.ProductNotInStockException;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_generator")
    @SequenceGenerator(name="product_generator", sequenceName = "product_seq", allocationSize=50, initialValue = 10)
    private long id;

    @Column(length = 50, name = "name")
    @NotBlank
    private String name;

    @Column(name = "price")
    @NotNull
    private float price;

    @Column(name = "quantity")
    @NotNull
    private long quantity;

    @Column(name = "image_url")
    private String imageUrl;

    public Product(String name, float price, long quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = "";
    }

    public Product(String name, float price, long quantity, String imageUrl) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }

    public boolean isAvailable() {
        return quantity > 0;
    }

    public void decreaseQuantity(int decrease) throws ProductNotInStockException {
        if (quantity < decrease) {
            throw new ProductNotInStockException(name);
        }
        quantity -= decrease;
    }

    public void increaseQuantity(int increase) {
        if (increase < 1) {
            return;
        }
        quantity += increase;
    }
}
