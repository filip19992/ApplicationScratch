package pl.filipwlodarczyk.SpringSecurity.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.filipwlodarczyk.SpringSecurity.model.AppUser;
import pl.filipwlodarczyk.SpringSecurity.model.Role;
import pl.filipwlodarczyk.SpringSecurity.registration.token.ConfirmationTokenService;
import pl.filipwlodarczyk.SpringSecurity.repo.RoleRepo;
import pl.filipwlodarczyk.SpringSecurity.repo.UserRepo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class UserServiceImplementation implements UserService, UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public AppUser saveUser(AppUser user) {

        boolean userExists = userRepo.findByUsername(user.getUsername()).isPresent();
        if (userExists) {
            log.info("Cant save user {}, credentials are taken", user.getUsername());
            throw new IllegalStateException("");
        } else {

            log.info("Saving new user {}", user.getName());
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            return userRepo.save(user);
        }
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {}", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void AddRoleToUser(String username, String roleName) {
        log.info("Saving new role {}, to the user {}", roleName, username);
        AppUser byUsername = userRepo.findByUsername(username).get();
        byUsername.getRoles().add(roleRepo.findByName(roleName));

    }

    @Override
    public AppUser getUserByUsername(String username) {
        log.info("Fetching user {}", username);
        return userRepo.findByUsername(username).get();
    }

    @Override
    public List<AppUser> getUsers() {
        log.info("Fetching all users");
        return userRepo.findAll();
    }

    @Override
    public void signUpUser(AppUser user) {
        boolean userExists = userRepo.findByUsername(user.getUsername()).isPresent();
        if (userExists) {
            log.info("Cant save user {}, credentials are taken", user.getUsername());
            throw new IllegalStateException("Credentials are taken");
        } else {

            log.info("Saving new user {}", user.getName());
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            userRepo.save(user);
        }

    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepo.findByUsername(username).get();
        if (user == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else if (!user.isEnabled()){
            log.error("User is not enabled consider checking your email confirmation");
            throw new InternalAuthenticationServiceException("User is not enabled consider checking your email confirmation");
        }

        else {
            log.info("User found in the database");
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), authorities);
    }

    @Override
    public int enableAppUser(String username) {
        return userRepo.enableAppUser(username);
    }
}
