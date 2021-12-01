package sep.webshopback.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import sep.webshopback.dtos.ProductDTO;
import sep.webshopback.exceptions.StoreNotFoundException;
import sep.webshopback.model.Product;
import sep.webshopback.model.User;
import sep.webshopback.service.ProductService;
import sep.webshopback.service.StoreService;
import sep.webshopback.service.UserService;

@Validated
@RestController
@RequestMapping("product")
public class ProductController {

    private final StoreService storeService;
    private final ProductService productService;
    private final UserService userService;
    
    @Autowired
    public ProductController(StoreService storeService, ProductService productService, UserService userService) {
        this.storeService = storeService;
        this.productService = productService;
        this.userService = userService;
    }
    
    @PostMapping(value="createProduct",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('SELLER')")
	public ResponseEntity<Void> createProduct(@RequestParam(name = "imageFile", required = false) MultipartFile data, @RequestParam(name = "post", required = false) String model) throws JsonMappingException, JsonProcessingException{
    	
    	User authenticated = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getById(authenticated.getId());
    	
        ObjectMapper mapper = new ObjectMapper();
    	ProductDTO productDTO = mapper.readValue(model, ProductDTO.class);
    	try {
			productService.createProduct(data, productDTO, user);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @GetMapping(value = "storesProducts")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<List<Product>> getProductsForOwnerId() throws StoreNotFoundException{
    	
    	User authenticated = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	User user = userService.getById(authenticated.getId());
        return new ResponseEntity<>(productService.getAllStoreProducts(user), HttpStatus.OK);

    }
    
}
