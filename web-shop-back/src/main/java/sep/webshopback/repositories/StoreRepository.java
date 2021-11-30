package sep.webshopback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sep.webshopback.model.Product;
import sep.webshopback.model.Store;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Store findStoreByOwnerId(long storeId);
    List<Store> findAllByName(String name);
}
