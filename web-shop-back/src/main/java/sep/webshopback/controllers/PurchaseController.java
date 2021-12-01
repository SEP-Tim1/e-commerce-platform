package sep.webshopback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sep.webshopback.exceptions.ProductNotFoundException;
import sep.webshopback.exceptions.ProductNotInStockException;
import sep.webshopback.model.PurchaseUserDetails;
import sep.webshopback.model.User;
import sep.webshopback.service.PurchaseService;

@RestController
@RequestMapping("purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService service;

    @PostMapping("/{cartId}")
    @PreAuthorize("hasRole('USER')")
    public void purchase(@PathVariable long cartId, @RequestBody PurchaseUserDetails details) {
        User authenticated = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            service.purchase(authenticated.getId(), cartId, details);
        } catch (ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (ProductNotInStockException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}