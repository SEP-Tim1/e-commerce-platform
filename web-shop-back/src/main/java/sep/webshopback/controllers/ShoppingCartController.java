package sep.webshopback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sep.webshopback.dtos.ProductQuantityChangeDTO;
import sep.webshopback.dtos.ShoppingCartDTO;
import sep.webshopback.exceptions.ProductNotFoundException;
import sep.webshopback.model.User;
import sep.webshopback.service.ShoppingCartService;

import java.util.List;

@RestController
@RequestMapping("cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService service;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ShoppingCartDTO>> getAll() {
        User authenticated = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ShoppingCartDTO> carts = service.getAllByUserId(authenticated.getId());
        return new ResponseEntity<>(carts, HttpStatus.OK);
    }

    @PostMapping("/{productId}")
    @PreAuthorize("hasRole('USER')")
    public void addToCart(@PathVariable long productId) {
        User authenticated = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            service.addToCart(authenticated.getId(), productId);
        } catch (ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('USER')")
    public void removeFromCart(@PathVariable long productId) {
        User authenticated = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            service.removeFromCart(authenticated.getId(), productId);
        } catch (ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    @PreAuthorize("hasRole('USER')")
    public void changeQuantity(@RequestBody ProductQuantityChangeDTO change) {
        User authenticated = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        service.changeQuantity(authenticated.getId(), change);
    }
}
