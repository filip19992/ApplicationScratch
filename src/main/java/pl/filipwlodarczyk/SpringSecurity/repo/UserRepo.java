package pl.filipwlodarczyk.SpringSecurity.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.filipwlodarczyk.SpringSecurity.model.AppUser;

import java.util.Optional;

public interface UserRepo extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);

}
