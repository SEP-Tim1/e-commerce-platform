package sep.webshopback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sep.webshopback.dtos.JwtDTO;
import sep.webshopback.dtos.LoginDTO;
import sep.webshopback.dtos.UserRegistrationDTO;
import sep.webshopback.model.User;
import sep.webshopback.security.TokenUtils;
import sep.webshopback.service.UserService;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = (User) authentication.getPrincipal();
            if (user.isAccountNonExpired() && user.isAccountNonLocked()) {
                String jwt = tokenUtils.generateToken(user.getUsername(), user.getRole().toString(), user.getId());
                return ResponseEntity.ok().body(new JwtDTO(jwt));
            }
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("Incorrect username or password");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() throws Exception {

        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        return ResponseEntity.ok("logout");
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody UserRegistrationDTO newUser){
        userService.registration(newUser);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    //Examples

    @GetMapping("/only-user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> onlyUser(){
        return ResponseEntity.ok("USER");
    }

    @GetMapping("/anyone")
    public ResponseEntity<?> anyone(){
        return ResponseEntity.ok("ANYONE");
    }
}
