package sep.webshopback.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sep.webshopback.model.Role;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserRegistrationDTO {

    @NotNull
    @Size(min=2, max=20)
    @Valid
    private String username;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min=8, max=200)
    private String password;

    @NotNull
    private Role role;
}
