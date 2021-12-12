package sep.webshopback.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sep.webshopback.client.PSPClient;
import sep.webshopback.dtos.*;
import sep.webshopback.exceptions.PaymentUnsuccessfulException;
import sep.webshopback.exceptions.ProductNotFoundException;
import sep.webshopback.exceptions.ProductNotInStockException;
import sep.webshopback.exceptions.TransactionNotFoundException;
import sep.webshopback.model.*;
import sep.webshopback.repositories.PurchaseRepository;
import sep.webshopback.repositories.ShoppingCartRepository;
import sep.webshopback.repositories.TransactionRepository;
import sep.webshopback.repositories.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
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
    @Autowired
    private TransactionRepository transactionRepository;
    @Value("${front.base.url}")
    private String frontUrl;

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
                cart.getStore(),
                cart
        );
        for(ProductQuantity p : cart.getProducts()) {
            purchase.addProduct(p);
        }
        purchase = repository.save(purchase);

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
                        )).collect(Collectors.toList()),
                        p.getStore().getName()
                )).sorted(Comparator.comparing(PurchaseDTO::getCreated))
                .collect(Collectors.toList());
    }

    private PaymentResponseIdDTO sendPaymentRequest(Purchase purchase) throws PaymentUnsuccessfulException {
        PaymentRequestDTO dto = new PaymentRequestDTO(
                purchase.getStore().getApiToken(),
                purchase.getId(),
                purchase.getCreated(),
                purchase.getTotal(),
                frontUrl + "/success/" + purchase.getId(),
                frontUrl + "/failure/" + purchase.getId(),
                frontUrl + "/error/" + purchase.getId()
        );
        try {
            PaymentResponseIdDTO response = pspClient.create(dto);
            log.info("Payment request (id=" + response.getRequestId() + ") created for purchase (id=" + purchase.getId() + ")");
            return response;
        } catch (Exception e) {
            throw new PaymentUnsuccessfulException("Payment service could not be started");
        }
    }

    public void saveTransaction(PaymentResponseDTO dto){
        Transaction transaction = new Transaction(
                dto.getMerchantOrderId(),
                dto.getTransactionStatus(),
                dto.getPaymentId(),
                dto.getErrorMessage());
        transactionRepository.save(transaction);
    }

    public PurchaseDTO purchaseSuccessful(long purchaseId) {
        Optional<Purchase> purchaseOpt =  repository.findById(purchaseId);
        if(purchaseOpt.isEmpty()) {
            return null;
        }
        Purchase p = purchaseOpt.get();
        ShoppingCart cart = p.getCart();
        p.setCart(null);
        cartRepository.delete(cart);
        log.info("Purchase (id=" + p.getId() + ") was successful");
        return new PurchaseDTO(
                p.getId(),
                p.getUserDetails(),
                p.getCreated(),
                p.getProducts().stream().map(prods -> new ProductQuantityDTO(
                        -1,
                        prods.getProduct().getName(),
                        prods.getProduct().getPrice(),
                        prods.getQuantity(),
                        prods.getTotal()
                )).collect(Collectors.toList()),
                p.getStore().getName()
        );
    }

    public void purchaseUnsuccessful(long purchaseId) {
        Optional<Purchase> purchaseOpt =  repository.findById(purchaseId);
        if(purchaseOpt.isEmpty()) {
            return;
        }
        Purchase purchase = purchaseOpt.get();
        for(ProductQuantity productQuantity: purchase.getCart().getProducts()) {
            productQuantity.getProduct().increaseQuantity(productQuantity.getQuantity());
        }
        ShoppingCart cart = purchase.getCart();
        purchase.setCart(null);
        cartRepository.delete(cart);
        repository.delete(purchase);
        log.info("Purchase (id=" + purchaseId + ") was successful. Deleted.");
    }

    public Transaction getTransaction(long purchaseId) throws TransactionNotFoundException {
        try {
            return transactionRepository.findByPurchaseId(purchaseId);
        } catch (Exception e) {
            throw new TransactionNotFoundException();
        }
    }
}
