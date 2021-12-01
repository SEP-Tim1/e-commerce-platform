package sep.webshopback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sep.webshopback.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    //@Query(value = "SELECT Product FROM Store.products WHERE Store.id = :storeId")

    @Query(value = "select products.id, products.name, products.price, products.quantity, products.image_url from products, stores_products, stores where products.id = stores_products.products_id and stores_products.store_id = :storeId", nativeQuery = true)
    List<Product> findProductsByStoreId(@Param("storeId") long storeId);
}
