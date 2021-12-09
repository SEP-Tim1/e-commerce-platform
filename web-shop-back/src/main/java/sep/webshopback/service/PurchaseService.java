package sep.webshopback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sep.webshopback.client.PSPClient;
import sep.webshopback.dtos.PaymentRequestDTO;
import sep.webshopback.dtos.PaymentResponseDTO;
import sep.webshopback.dtos.ProductQuantityDTO;
import sep.webshopback.dtos.PurchaseDTO;
import sep.webshopback.exceptions.PaymentUnsuccessfulException;
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
    @Autowired
    private PSPClient pspClient;

    @Transactional(rollbackOn = {ProductNotFoundException.class, ProductNotInStockException.class, PaymentUnsuccessfulException.class})
    public long purchase(long userId, long cartId, PurchaseUserDetails details) throws ProductNotFoundException, ProductNotInStockException, PaymentUnsuccessfulException {
        Optional<User> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            throw new ProductNotFoundException();
        }
        Optional<ShoppingCart> cartOpt = cartRepository.findById(cartId);
        if (!cartOpt.isPresent()) {
            throw new ProductNotFoundException();
        }
        User user = userOpt.get();
        ShoppingCart cart = cartOpt.get();
        if (user.getId() != cart.getUser().getId()) {
            throw new ProductNotFoundException();
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
        purchase = repository.save(purchase);
        cartRepository.delete(cart);

        return sendPaymentRequest(purchase).getRequestId();
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

    private PaymentResponseDTO sendPaymentRequest(Purchase purchase) throws PaymentUnsuccessfulException {
        PaymentRequestDTO dto = new PaymentRequestDTO(
                purchase.getStore().getApiToken(),
                purchase.getId(),
                purchase.getCreated(),
                purchase.getTotal(),
                "",
                "",
                ""
        );
        try {
            return pspClient.create(dto);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PaymentUnsuccessfulException("Payment service could not be started");
        }
    }
}
