package pl.filipwlodarczyk.SpringSecurity.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.filipwlodarczyk.SpringSecurity.model.AppUser;
import pl.filipwlodarczyk.SpringSecurity.model.RegistrationRequest;
import pl.filipwlodarczyk.SpringSecurity.registration.token.ConfirmationToken;
import pl.filipwlodarczyk.SpringSecurity.registration.token.ConfirmationTokenService;
import pl.filipwlodarczyk.SpringSecurity.utils.EmailValidator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {

    private final UserService userService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;


    public String register(RegistrationRequest request) throws Exception {
        if(emailValidator.test(request.getEmail())) {

            AppUser appUser = new AppUser(null, request.getName(), request.getUsername(),
                    request.getPassword(), false, false,
                    new ArrayList<>());

            userService.signUpUser((appUser));

            userService.AddRoleToUser(request.getUsername(), "ROLE_USER");

            String token = UUID.randomUUID().toString();
            ConfirmationToken confirmationToken = new ConfirmationToken(
                    token,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(15),
                    appUser
            );

            confirmationTokenService.saveConfirmationToken(confirmationToken);
            return token;

        } else {
            log.error("Cant register new user, something is wrong with credentials");
            throw new Exception("Cant register user");
        }

    }
}
