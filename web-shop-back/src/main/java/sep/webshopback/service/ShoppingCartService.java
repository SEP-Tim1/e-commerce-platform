package sep.webshopback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sep.webshopback.dtos.ProductQuantityChangeDTO;
import sep.webshopback.dtos.ProductQuantityDTO;
import sep.webshopback.dtos.ShoppingCartDTO;
import sep.webshopback.exceptions.ProductNotFoundException;
import sep.webshopback.model.*;
import sep.webshopback.repositories.ProductRepository;
import sep.webshopback.repositories.ShoppingCartRepository;
import sep.webshopback.repositories.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository repository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StoreService storeService;
    @Autowired
    private UserRepository userRepository;

    public List<ShoppingCartDTO> getAllByUserId(long userId) {
        return repository.findAllByUserId(userId).stream().map(sc -> new ShoppingCartDTO(
                sc.getId(),
                sc.getStore().getName(),
                sc.getProducts().stream().map(p -> new ProductQuantityDTO(
                        p.getProduct().getId(),
                        p.getProduct().getName(),
                        p.getProduct().getPrice(),
                        p.getQuantity(),
                        p.getTotal()
                )).collect(Collectors.toList()),
                sc.getTotal()
        )).collect(Collectors.toList());
    }

    public void addToCart(long userId, long productId) throws ProductNotFoundException {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (!productOpt.isPresent()) {
            throw new ProductNotFoundException();
        }
        Product product = productOpt.get();
        Store store = storeService.getByProductId(productId);
        if (store == null) {
            throw new ProductNotFoundException();
        }

        ShoppingCart cart = repository.findByUserIdAndStoreId(userId, store.getId());
        if (cart == null) {
            cart = createCart(userId, store, product);
        } else {
            cart.appendProduct(product);
        }
        if (cart != null) {
            repository.save(cart);
        }
    }

    public void removeFromCart(long userId, long productId) throws ProductNotFoundException {
        if(!productRepository.existsById(productId)) {
            throw new ProductNotFoundException();
        }
        Store store = storeService.getByProductId(productId);
        if (store == null) {
            throw new ProductNotFoundException();
        }
        ShoppingCart cart = repository.findByUserIdAndStoreId(userId, store.getId());
        if (cart == null) {
            return;
        }
        cart.removeProductById(productId);
        if (cart.isEmpty()) {
            repository.delete(cart);
        } else {
            repository.save(cart);
        }
    }

    public void changeQuantity(long userId, ProductQuantityChangeDTO change) {
        Optional<ShoppingCart> cartOpt = repository.findById(change.getCartId());
        if (!cartOpt.isPresent()) {
            return;
        }
        ShoppingCart cart = cartOpt.get();
        if (cart.getUser().getId() != userId) {
            return;
        }
        cart.changeQuantityByProductId(change.getProductId(), change.getNewQuantity());
        repository.save(cart);
    }

    private ShoppingCart createCart(long userId, Store store, Product product) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            return null;
        }
        User user = userOpt.get();
        ShoppingCart cart = new ShoppingCart(
                user,
                store,
                Arrays.asList(new ProductQuantity(product, 1))
        );
        return cart;
    }
}
