package sep.webshopback.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sep.webshopback.client.PSPClient;
import sep.webshopback.dtos.*;
import sep.webshopback.exceptions.PaymentUnsuccessfulException;
import sep.webshopback.exceptions.ProductNotFoundException;
import sep.webshopback.exceptions.ProductNotInStockException;
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
                        )).collect(Collectors.toList())
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
            return pspClient.create(dto);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PaymentUnsuccessfulException("Payment service could not be started");
        }
    }

    public void saveTransaction(PaymentResponseDTO dto){
        Transaction transaction = new Transaction(dto.getMerchantOrderId(), dto.getTransactionStatus(), dto.getPaymentId());
        transactionRepository.save(transaction);
    }

    public void purchaseSuccessful(long purchaseId) {
        Optional<Purchase> purchaseOpt =  repository.findById(purchaseId);
        if(purchaseOpt.isEmpty()) {
            return;
        }
        Purchase purchase = purchaseOpt.get();
        ShoppingCart cart = purchase.getCart();
        purchase.setCart(null);
        cartRepository.delete(cart);
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
        repository.delete(purchase);
    }
}
