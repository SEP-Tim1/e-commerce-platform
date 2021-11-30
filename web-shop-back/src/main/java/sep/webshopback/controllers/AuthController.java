package sep.webshopback.controllers;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sep.webshopback.dtos.JwtDTO;
import sep.webshopback.dtos.LoginDTO;
import sep.webshopback.dtos.NewInfoDTO;
import sep.webshopback.dtos.UserRegistrationDTO;
import sep.webshopback.exceptions.EmailNotUniqueException;
import sep.webshopback.exceptions.UsernameNotUniqueException;
import sep.webshopback.model.User;
import sep.webshopback.security.TokenUtils;
import sep.webshopback.service.UserService;

import javax.validation.Valid;
import java.net.URI;
import java.util.Map;

@Validated
@RestController
@RequestMapping("auth")
public class AuthController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final TokenUtils tokenUtils;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, TokenUtils tokenUtils) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenUtils = tokenUtils;
    }

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
    public ResponseEntity<?> logout() {

        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        return ResponseEntity.ok("logout");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegistrationDTO userDTO) {
        try {
            User user = userService.registration(userDTO);
            return ResponseEntity.created(URI.create("/user/" + user.getId())).build();
        }
        catch (UsernameNotUniqueException | EmailNotUniqueException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", e.getMessage(),
                    "cause", e.getCause().getMessage())
            );
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Bad request.");
        }
    }

    @PostMapping("/update")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateInfo(@Valid @RequestBody NewInfoDTO infoDTO) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userService.updateInfo(infoDTO, user.getId());
            return ResponseEntity.ok("Updated.");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Bad request.");
        }
    }

    @GetMapping("/info")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateInfo() {
        try {
            User authenticated = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userService.getById(authenticated.getId());
            return ResponseEntity.ok(new NewInfoDTO(user.getFirstName(), user.getLastName(), user.getPhone(), user.getAddress()));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Bad request.");
        }
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
