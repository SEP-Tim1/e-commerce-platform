package sep.webshopback.dtos;

import sep.webshopback.model.Product;
import sep.webshopback.model.Store;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class StoreDTO {
    private long id;

    private String name;

    private List<Product> products = new ArrayList<>();

    private long owner;

    public StoreDTO(){}

    public StoreDTO(long id, String name, List<Product> products, long owner) {
        this.id = id;
        this.name = name;
        this.products = products;
        this.owner = owner;
    }

    public StoreDTO(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.products = store.getProducts();
        this.owner = store.getOwner().getId();
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public long getOwner() {
        return owner;
    }

    public void setOwner(long owner) {
        this.owner = owner;
    }
}
