package pl.filipwlodarczyk.SpringSecurity.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.filipwlodarczyk.SpringSecurity.model.AppUser;

public interface UserRepo extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);
}
