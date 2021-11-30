package sep.webshopback.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sep.webshopback.service.StoreService;

@RestController
@RequestMapping("product")
public class ProductController {

    private final StoreService storeService;

    public ProductController(StoreService storeService) {
        this.storeService = storeService;
    }
}
