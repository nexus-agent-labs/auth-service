package authservice.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import authservice.user.domain.User;
import java.util.Optional;




public interface AuthRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
