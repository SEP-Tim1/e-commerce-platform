package sep.webshopback.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import sep.webshopback.dtos.ProductDTO;
import sep.webshopback.exceptions.ProductNotFoundException;
import sep.webshopback.exceptions.StoreNotFoundException;
import sep.webshopback.model.Product;
import sep.webshopback.model.Store;
import sep.webshopback.model.User;
import sep.webshopback.repositories.ProductRepository;
import sep.webshopback.repositories.StoreRepository;

@Service
public class ProductService {

	private ProductRepository productRepository;
	private StoreRepository storeRepository;
	
	@Value("${media.storage}")
	private String storageDirectoryPath;
	
	@Autowired
    public ProductService(ProductRepository productRepository, StoreRepository storeRepository) {
		this.productRepository = productRepository;
		this.storeRepository = storeRepository;
	}
	 
	public Product getProductById(long id) throws ProductNotFoundException {
	        if(productRepository.findById(id).isPresent()) return productRepository.findById(id).get();
	        throw new ProductNotFoundException();
	    }
	
	public void createProduct(MultipartFile file, ProductDTO dto, User user) throws IOException {
		String fileName = saveFile(file, storageDirectoryPath);
		String fileDownloadUri = "media/content/" + fileName;
		System.out.println(fileDownloadUri);
		
		Product product = new Product();
		product.setName(dto.getName());
		product.setPrice(Float.parseFloat(dto.getPrice()));
		product.setQuantity(dto.getQuantity());
		product.setImageUrl(fileDownloadUri);
		productRepository.save(product);
		
		Store store = getStoreByStoreOwner(user);
		store.getProducts().add(product);
		storeRepository.save(store);
	}
	
	private String saveFile(MultipartFile file, String storageDirectoryPath) throws IOException {
		String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
		String extension = getFileExtension(originalFileName);
		String fileName = UUID.randomUUID().toString() + "." + extension;

		System.out.println(fileName);

		Path storageDirectory = Paths.get(storageDirectoryPath);
		if(!Files.exists(storageDirectory)){
			Files.createDirectories(storageDirectory);
		}
		Path destination = Paths.get(storageDirectory.toString() + File.separator + fileName);
		Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
		return fileName;
	}
	
	private String getFileExtension(String fileName) throws IOException {
		String[] parts = fileName.split("\\.");
		if(parts.length > 0)
			return parts[parts.length - 1];
		else
			throw new IOException();
	}

	private Store getStoreByStoreOwner(User user) {
		Store store = storeRepository.findAll().stream().filter(s -> s.getOwner().getId() == user.getId()).findFirst().orElse(null);
		return store;
	}
	
	 public List<Product> getProductsInStore(long storeId) throws StoreNotFoundException {
	        if (storeRepository.findById(storeId).isPresent()) return productRepository.findProductsByStoreId(storeId);
	        throw new StoreNotFoundException();
	    }
	 
	 public List<Product> getAllStoreProducts(User user) throws StoreNotFoundException {
		Store store = storeRepository.findAll().stream().filter(s -> s.getOwner().getId() == user.getId()).findFirst().orElse(null);
		List<Product> products = getProductsInStore(store.getId());
		return products;
	 }
}
