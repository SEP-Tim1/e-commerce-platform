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
import sep.webshopback.exceptions.PurchaseNotFoundException;
import sep.webshopback.model.*;
import sep.webshopback.repositories.ProductRepository;
import sep.webshopback.repositories.PurchaseRepository;
import sep.webshopback.repositories.ShoppingCartRepository;
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
    private ProductRepository productRepository;
    @Autowired
    private PSPClient pspClient;
    @Value("${front.base.url}")
    private String frontUrl;
    @Value("${server.host}")
    private String host;
    @Value("${server.port}")
    int port;

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
            productRepository.save(p.getProduct());
        }
        Purchase purchase = new Purchase(details, LocalDateTime.now(), cart);
        purchase = repository.save(purchase);

        long paymentRequestId = sendPaymentRequest(purchase).getRequestId();
        return paymentRequestId;
    }

    public List<PurchaseDTO> getAll(long userId) {
        return repository.findAllByCartStoreOwnerId(userId).stream()
                .filter(p -> p.getOutcome().getStatus() == PurchaseStatus.SUCCESS)
                .map(p -> new PurchaseDTO(
                        p.getId(),
                        p.getUserDetails(),
                        p.getCreated(),
                        p.getCart().getProducts().stream().map(prods -> new ProductQuantityDTO(
                                -1,
                                prods.getProduct().getName(),
                                prods.getProduct().getPrice(p.getCreated()),
                                prods.getQuantity(),
                                prods.getTotal(p.getCreated())
                        )).collect(Collectors.toList()),
                        p.getCart().getStore().getName()
                )).sorted(Comparator.comparing(PurchaseDTO::getCreated))
                .collect(Collectors.toList());
    }

    private PaymentResponseIdDTO sendPaymentRequest(Purchase purchase) throws PaymentUnsuccessfulException {
        PaymentRequestDTO dto = new PaymentRequestDTO(
                purchase.getCart().getStore().getApiToken(),
                purchase.getId(),
                purchase.getCreated(),
                purchase.getTotal(),
                "RSD",
                frontUrl + "/success/" + purchase.getId(),
                frontUrl + "/failure/" + purchase.getId(),
                frontUrl + "/error/" + purchase.getId(),
                "http://"+ host +":" + port + "/purchase/outcome"
        );
        try {
            PaymentResponseIdDTO response = pspClient.create(dto);
            log.info("Payment request (id=" + response.getRequestId() + ") created for purchase (id=" + purchase.getId() + ")");
            return response;
        } catch (Exception e) {
            throw new PaymentUnsuccessfulException("Payment service could not be started");
        }
    }

    public void processPaymentOutcome(PaymentResponseDTO dto){
        Optional<Purchase> purchaseOpt =  repository.findById(dto.getMerchantOrderId());
        if(purchaseOpt.isEmpty()) { return; }
        Purchase p = purchaseOpt.get();
        if(p.getOutcome() != null) { return; }
        p.setOutcome(new PurchaseOutcome(dto.getStatus(), dto.getMessage()));
        repository.save(p);
        log.info("Purchase (id=" + p.getId() + ") status modified to: " + p.getOutcome().getStatus());
        if (dto.getStatus() != PurchaseStatus.SUCCESS) {
            purchaseUnsuccessful(p);
        }
    }

    public PurchaseOutcome getOutcome(long purchaseId) throws PurchaseNotFoundException {
        Optional<Purchase> purchase = repository.findById(purchaseId);
        if (purchase.isEmpty()) {
            throw new PurchaseNotFoundException("Purchase not found");
        }
        return purchase.get().getOutcome();
    }

    public PurchaseDTO get(long id) throws PurchaseNotFoundException {
        Optional<Purchase> purchase = repository.findById(id);
        if (purchase.isEmpty()) {
            throw new PurchaseNotFoundException("Purchase not found");
        }
        if (purchase.get().getOutcome() == null || purchase.get().getOutcome().getStatus() != PurchaseStatus.SUCCESS) {
            throw new PurchaseNotFoundException("Purchase not found");
        }
        return new PurchaseDTO(
                purchase.get().getId(),
                purchase.get().getUserDetails(),
                purchase.get().getCreated(),
                purchase.get().getCart().getProducts().stream().map(
                        p -> new ProductQuantityDTO(
                                p.getProduct().getId(),
                                p.getProduct().getName(),
                                p.getProduct().getPrice(purchase.get().getCreated()),
                                p.getQuantity(),
                                p.getTotal(purchase.get().getCreated()))
                ).collect(Collectors.toList()),
                purchase.get().getCart().getStore().getName()
        );
    }

    private void purchaseUnsuccessful(Purchase p) {
        for(ProductQuantity productQuantity: p.getCart().getProducts()) {
            Product product = productQuantity.getProduct();
            product.increaseQuantity(productQuantity.getQuantity());
            productRepository.save(product);
        }
    }
}
