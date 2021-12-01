package sep.webshopback.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "products")
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

    public Product(){}

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isAvailable() {
        return quantity > 0;
    }
}
