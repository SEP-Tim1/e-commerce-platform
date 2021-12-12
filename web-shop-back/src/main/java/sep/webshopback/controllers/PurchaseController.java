package sep.webshopback.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sep.webshopback.dtos.PaymentResponseDTO;
import sep.webshopback.dtos.PurchaseDTO;
import sep.webshopback.exceptions.PaymentUnsuccessfulException;
import sep.webshopback.exceptions.ProductNotFoundException;
import sep.webshopback.exceptions.ProductNotInStockException;
import sep.webshopback.model.PurchaseUserDetails;
import sep.webshopback.model.User;
import sep.webshopback.service.PurchaseService;

import java.util.List;

@RestController
@RequestMapping("purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService service;

    @PostMapping("/{cartId}")
    @PreAuthorize("hasRole('USER')")
    public long purchase(@PathVariable long cartId, @RequestBody PurchaseUserDetails details) {
        User authenticated = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            return service.purchase(authenticated.getId(), cartId, details);
        } catch (ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (ProductNotInStockException | PaymentUnsuccessfulException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("bank-payment-response")
    public void bankPaymentResponse(@RequestBody PaymentResponseDTO dto){
        service.saveTransaction(dto);
    };

    @GetMapping
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<List<PurchaseDTO>> getAll() {
        User authenticated = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<PurchaseDTO> result = service.getAll(authenticated.getId());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("success/{purchaseId}")
    public PurchaseDTO purchaseSuccessful(@PathVariable long purchaseId) {
        return service.purchaseSuccessful(purchaseId);
    }

    @PutMapping("failure/{purchaseId}")
    public void purchaseUnsuccessful(@PathVariable long purchaseId) {
        service.purchaseUnsuccessful(purchaseId);
    }
}
