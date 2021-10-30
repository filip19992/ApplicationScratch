package pl.filipwlodarczyk.SpringSecurity;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.filipwlodarczyk.SpringSecurity.model.AppUser;
import pl.filipwlodarczyk.SpringSecurity.model.Role;
import pl.filipwlodarczyk.SpringSecurity.service.UserService;

import java.util.ArrayList;

@SpringBootApplication
@EnableAutoConfiguration
@RequiredArgsConstructor
public class SpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
    }



    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {

            userService.saveRole(new Role(null, "ROLE_ADMIN"));
            userService.saveRole(new Role(null, "ROLE_USER"));
            userService.saveRole(new Role(null, "ROLE_MANAGER"));

            userService.saveUser(new AppUser(null, "Filip Wlodarczyk", "filip", new BCryptPasswordEncoder().encode("password"),
                    new ArrayList<>()));
            userService.saveUser(new AppUser(null, "Maciek Szewczyk", "maciek", new BCryptPasswordEncoder().encode("password"),
                    new ArrayList<>()));
            userService.saveUser(new AppUser(null, "Szymon Steplewski", "szymon", new BCryptPasswordEncoder().encode("password"),
                    new ArrayList<>()));
            userService.saveUser(new AppUser(null, "Kuba Górski", "kuba", new BCryptPasswordEncoder().encode("password"),
                    new ArrayList<>()));

            userService.AddRoleToUser("filip", "ROLE_ADMIN");
            userService.AddRoleToUser("maciek", "ROLE_ADMIN");
            userService.AddRoleToUser("szymon", "ROLE_MANAGER");
            userService.AddRoleToUser("kuba", "ROLE_USER");

        };
    }
}
