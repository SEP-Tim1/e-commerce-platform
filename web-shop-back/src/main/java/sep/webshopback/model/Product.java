package sep.webshopback.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sep.webshopback.exceptions.ProductNotInStockException;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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

    @Column(name = "quantity")
    @NotNull
    private long quantity;

    @ElementCollection
    private List<Price> priceList;

    @Column(name = "image_url")
    private String imageUrl;

    public Product(String name, long quantity) {
        this.name = name;
        this.quantity = quantity;
        this.imageUrl = "";
    }

    public Product(String name, long quantity, String imageUrl) {
        this.name = name;
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

    public float getCurrentPrice() {
        if (priceList == null) {
            priceList = new ArrayList<>();
        }
        Optional<Price> price = priceList.stream()
                .max(Comparator.comparing(Price::getActiveFrom));
        if (price.isEmpty()) {
            return 0;
        }
        return price.get().getPrice();
    }

    public float getPrice(LocalDateTime timePoint) {
        if (priceList == null) {
            priceList = new ArrayList<>();
        }
        Optional<Price> price = priceList.stream()
                .filter(p -> p.getActiveFrom().isBefore(timePoint))
                .max(Comparator.comparing(Price::getActiveFrom));
        if (price.isEmpty()) {
            return 0;
        }
        return price.get().getPrice();
    }

    public void setPrice(float price) {
        if (priceList == null) {
            priceList = new ArrayList<>();
        }
        Price newPrice = new Price(price, LocalDateTime.now());
        priceList.add(newPrice);
    }
}
