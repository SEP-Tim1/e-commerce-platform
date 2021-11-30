package sep.webshopback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sep.webshopback.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
