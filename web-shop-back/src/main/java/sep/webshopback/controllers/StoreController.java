package sep.webshopback.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sep.webshopback.dtos.StoreDTO;
import sep.webshopback.exceptions.StoreNotFoundException;
import sep.webshopback.model.Product;
import sep.webshopback.model.Store;
import sep.webshopback.service.ProductService;
import sep.webshopback.service.StoreService;
import sep.webshopback.util.UserToken;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("store")
public class StoreController {

    private final StoreService storeService;
    private final ProductService productService;

    public StoreController(StoreService storeService, ProductService productService) {
        this.storeService = storeService;
        this.productService = productService;
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
            List<Product> products = productService.getProductsInStore(id);
            return ResponseEntity.ok(products);
        } catch (StoreNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/name/{ownerId}")
    public ResponseEntity<?> getStoreNameByOwnerId(@PathVariable long ownerId) {
        try {
            String name = storeService.getStoreNameByOwnerId(ownerId);
            return ResponseEntity.ok(new StoreNameDTO(name));
        } catch (StoreNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/name")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<?> setStoreNameByOwnerId(@Valid @RequestBody StoreNameDTO name) {
        try {
            storeService.setStoreNameByOwnerId(UserToken.getUserIdFromToken(), name.getName());
            return ResponseEntity.ok("Store name is updated.");
        } catch (StoreNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}

