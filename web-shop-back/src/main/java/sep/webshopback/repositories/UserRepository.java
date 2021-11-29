package sep.webshopback.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sep.webshopback.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
    User findUserByEmail(String email);
}
