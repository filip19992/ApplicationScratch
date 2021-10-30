package pl.filipwlodarczyk.SpringSecurity.service;

import pl.filipwlodarczyk.SpringSecurity.model.Role;
import pl.filipwlodarczyk.SpringSecurity.model.AppUser;

import java.util.List;


public interface UserService {
    AppUser saveUser(AppUser user);
    Role saveRole(Role role);
    void AddRoleToUser(String username, String roleName);
    AppUser getUserByUsername(String username);
    List<AppUser> getUsers();
}
