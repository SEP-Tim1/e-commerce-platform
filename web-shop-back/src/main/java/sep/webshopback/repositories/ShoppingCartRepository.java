package sep.webshopback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sep.webshopback.model.ShoppingCart;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    List<ShoppingCart> findAllByUserId(long userId);

    ShoppingCart findByUserIdAndStoreId(long userId, long storeId);
}
