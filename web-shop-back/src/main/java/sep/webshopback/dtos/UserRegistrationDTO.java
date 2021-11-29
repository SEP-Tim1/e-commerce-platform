package sep.webshopback.dtos;

import sep.webshopback.model.Role;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegistrationDTO {

    @NotNull
    @Size(min=2, max=20)
    private String username;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min=8, max=200)
    private String password;

    @NotNull
    private Role role;

    public UserRegistrationDTO(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(@Valid String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
