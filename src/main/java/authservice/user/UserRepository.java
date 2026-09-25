package authservice.user;

import org.springframework.data.jpa.repository.JpaRepository;

import authservice.user.domain.User;
import java.util.Optional;




public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
