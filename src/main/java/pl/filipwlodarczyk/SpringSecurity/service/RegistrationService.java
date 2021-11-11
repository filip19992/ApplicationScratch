package pl.filipwlodarczyk.SpringSecurity.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.filipwlodarczyk.SpringSecurity.model.AppUser;
import pl.filipwlodarczyk.SpringSecurity.model.RegistrationRequest;
import pl.filipwlodarczyk.SpringSecurity.utils.EmailValidator;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {

    private final UserService userService;
    private final EmailValidator emailValidator;


    public void register(RegistrationRequest request) {
        if(emailValidator.test(request.getEmail())) {
            AppUser newAppUser = userService.saveUser(new AppUser(null, request.getName(), request.getUsername(), request.getPassword(),
                    new ArrayList<>()));


            userService.AddRoleToUser(newAppUser.getUsername(), "ROLE_USER");

            userService.saveUser(newAppUser);
        } else {
            log.error("Cant register new user, something is wrong with credentials");
        }

    }
}
