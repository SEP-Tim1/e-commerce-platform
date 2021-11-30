package sep.webshopback.dtos;

import javax.validation.constraints.Size;

public class NewInfoDTO {

    @Size(max=50)
    private String firstName;

    @Size(max=50)
    private String lastName;

    @Size(max=20)
    private String phone;

    @Size(max=200)
    private String address;

    public NewInfoDTO(){}

    public NewInfoDTO(String firstName, String lastName, String phone, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
