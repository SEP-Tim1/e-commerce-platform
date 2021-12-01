package sep.webshopback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sep.webshopback.dtos.ProductQuantityDTO;
import sep.webshopback.dtos.PurchaseDTO;
import sep.webshopback.exceptions.ProductNotFoundException;
import sep.webshopback.exceptions.ProductNotInStockException;
import sep.webshopback.model.*;
import sep.webshopback.repositories.PurchaseRepository;
import sep.webshopback.repositories.ShoppingCartRepository;
import sep.webshopback.repositories.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository repository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShoppingCartRepository cartRepository;

    @Transactional(rollbackOn = {ProductNotFoundException.class, ProductNotInStockException.class})
    public void purchase(long userId, long cartId, PurchaseUserDetails details) throws ProductNotFoundException, ProductNotInStockException {
        Optional<User> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            return;
        }
        Optional<ShoppingCart> cartOpt = cartRepository.findById(cartId);
        if (!cartOpt.isPresent()) {
            return;
        }
        User user = userOpt.get();
        ShoppingCart cart = cartOpt.get();
        if (user.getId() != cart.getUser().getId()) {
            return;
        }

        for(ProductQuantity p : cart.getProducts()) {
            cart.getStore().decreaseQuantity(p.getProduct(), p.getQuantity());
        }
        Purchase purchase = new Purchase(
                details,
                LocalDateTime.now(),
                cart.getStore()
        );
        for(ProductQuantity p : cart.getProducts()) {
            purchase.addProduct(p);
        }
        repository.save(purchase);
        cartRepository.delete(cart);

        //preusmeri na placanje
        //rollback svega ako je bilo koji korak neuspesan
    }

    public List<PurchaseDTO> getAll(long userId) {
        return repository.findAllByStoreOwnerId(userId).stream()
                .map(p -> new PurchaseDTO(
                        p.getId(),
                        p.getUserDetails(),
                        p.getCreated(),
                        p.getProducts().stream().map(prods -> new ProductQuantityDTO(
                                -1,
                                prods.getProduct().getName(),
                                prods.getProduct().getPrice(),
                                prods.getQuantity(),
                                prods.getTotal()
                        )).collect(Collectors.toList())
                )).sorted(Comparator.comparing(PurchaseDTO::getCreated))
                .collect(Collectors.toList());
    }


}
