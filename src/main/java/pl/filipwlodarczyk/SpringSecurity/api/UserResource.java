package pl.filipwlodarczyk.SpringSecurity.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.filipwlodarczyk.SpringSecurity.model.AppUser;
import pl.filipwlodarczyk.SpringSecurity.model.Role;
import pl.filipwlodarczyk.SpringSecurity.service.UserService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class UserResource {

    private final UserService userService;

    @GetMapping("users")
    public ResponseEntity<List<AppUser>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("user/save")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser appUser) {

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(appUser));
    }

    @PostMapping("role/save")
    public ResponseEntity<Role> saveUser(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @GetMapping("user/")
    public ResponseEntity<AppUser> getUserByUsername(String username) {
        return ResponseEntity.ok().body(userService.getUserByUsername(username));
    }

    @PostMapping("role/addtouser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm roleToUserForm) {

        userService.AddRoleToUser(roleToUserForm.getUsername(), roleToUserForm.getRoleName());
       return ResponseEntity.ok().build();
    }


}

@Data
class RoleToUserForm {
    private String roleName;
    private String username;
}
