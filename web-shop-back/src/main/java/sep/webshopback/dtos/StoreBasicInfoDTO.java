package sep.webshopback.dtos;

import java.util.List;

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

    public StoreBasicInfoDTO(long id, String name, String email, String phoneNumber, List<ProductUserViewDTO> products) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.products = products;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<ProductUserViewDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductUserViewDTO> products) {
        this.products = products;
    }
}
