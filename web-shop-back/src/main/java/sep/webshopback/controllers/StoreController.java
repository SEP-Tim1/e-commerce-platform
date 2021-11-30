package sep.webshopback.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sep.webshopback.dtos.StoreDTO;
import sep.webshopback.exceptions.StoreNotFoundException;
import sep.webshopback.model.Product;
import sep.webshopback.model.Store;
import sep.webshopback.service.StoreService;

import java.util.List;

@RestController
@RequestMapping("store")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/by-owner/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getByOwnerId(@PathVariable long id) {
        try {
            Store store = storeService.getByOwnerId(id);
            return ResponseEntity.ok(new StoreDTO(store));
        } catch (StoreNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<?> getProducts(@PathVariable long id) {
        try {
            List<Product> products = storeService.getProductsInStore(id);
            return ResponseEntity.ok(products);
        } catch (StoreNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}

