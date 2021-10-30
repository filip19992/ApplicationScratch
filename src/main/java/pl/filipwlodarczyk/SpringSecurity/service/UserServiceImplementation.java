package pl.filipwlodarczyk.SpringSecurity.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.filipwlodarczyk.SpringSecurity.model.Role;
import pl.filipwlodarczyk.SpringSecurity.model.AppUser;
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

    @Override
    public AppUser saveUser(AppUser user) {
        log.info("Saving new user {}", user.getName());
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {}", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void AddRoleToUser(String username, String roleName) {
        log.info("Saving new role {}, to the user {}", roleName, username);
        AppUser byUsername = userRepo.findByUsername(username);
        byUsername.getRoles().add(roleRepo.findByName(roleName));

    }

    @Override
    public AppUser getUserByUsername(String username) {
        log.info("Fetching user {}", username);
        return userRepo.findByUsername(username);
    }

    @Override
    public List<AppUser> getUsers() {
        log.info("Fetching all users");
        return userRepo.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       AppUser user = userRepo.findByUsername(username);
       if(user == null) {
           log.error("User not found in the database");
           throw new UsernameNotFoundException("User not found in the database");
       } else {
           log.info("User found in the database");
       }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
       user.getRoles().forEach(role -> {
           authorities.add(new SimpleGrantedAuthority(role.getName()));});
       return new org.springframework.security.core.userdetails.User(user.getUsername(),
               user.getPassword(), authorities);
    }
}
