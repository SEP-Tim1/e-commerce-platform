package sep.webshopback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sep.webshopback.model.Purchase;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findAllByCartStoreOwnerId(long ownerId);
}
