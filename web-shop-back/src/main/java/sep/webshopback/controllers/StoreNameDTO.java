package sep.webshopback.controllers;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class StoreNameDTO {

    @Size(min=2, max=50)
    @NotBlank
    private String name;

    public StoreNameDTO(){}

    public StoreNameDTO(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
