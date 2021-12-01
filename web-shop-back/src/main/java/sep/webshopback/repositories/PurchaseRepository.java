package sep.webshopback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sep.webshopback.model.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
