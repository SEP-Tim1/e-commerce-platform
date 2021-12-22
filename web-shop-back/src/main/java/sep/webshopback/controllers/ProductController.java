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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import sep.webshopback.dtos.ProductDTO;
import sep.webshopback.dtos.ProductUpdateDTO;
import sep.webshopback.dtos.UpdateProductDTO;
import sep.webshopback.exceptions.ProductNotFoundException;
import sep.webshopback.exceptions.StoreNotFoundException;
import sep.webshopback.model.Product;
import sep.webshopback.model.User;
import sep.webshopback.service.ProductService;
import sep.webshopback.service.StoreService;
import sep.webshopback.service.UserService;

@Validated
@RestController
@CrossOrigin
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
    public ResponseEntity<List<UpdateProductDTO>> getProductsForOwnerId() throws StoreNotFoundException{
    	
    	User authenticated = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	User user = userService.getById(authenticated.getId());
        return new ResponseEntity<>(productService.getAllStoreProducts(user), HttpStatus.OK);

    }
    
    @GetMapping(value = "{id}")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<UpdateProductDTO> getProductById(@PathVariable long id){
        try {
			return new ResponseEntity<>(productService.getById(id), HttpStatus.OK);
		} catch (ProductNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
    }
    
    @PostMapping(value="updateProductImage",  consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('SELLER')")
	public ResponseEntity<Void> updateProductImage(@RequestParam(name = "imageFile", required = false) MultipartFile data, @RequestParam(name = "post", required = false) String model) throws JsonMappingException, JsonProcessingException{
    	    	
        ObjectMapper mapper = new ObjectMapper();
    	ProductUpdateDTO productDTO = mapper.readValue(model, ProductUpdateDTO.class);
    	try {
			productService.updateProductImage(data, productDTO);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		} catch (ProductNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PostMapping(value = "updateProductInfo")
    @PreAuthorize("hasRole('SELLER')")
	public ResponseEntity<Void> updateProductInfo(@RequestBody UpdateProductDTO product) {
    	try {
			productService.updateProductInfo(product);
		} catch (ProductNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PostMapping(value = "deleteProduct")
    @PreAuthorize("hasRole('SELLER')")
	public ResponseEntity<Void> deleteProduct(@RequestBody long productId) {
    	User authenticated = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	User user = userService.getById(authenticated.getId());
    	try {
			productService.deleteProduct(productId,user);
		} catch (ProductNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
    	return new ResponseEntity<>(HttpStatus.OK);
    }
    
}
