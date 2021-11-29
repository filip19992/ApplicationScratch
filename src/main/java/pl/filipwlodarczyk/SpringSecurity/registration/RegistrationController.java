package pl.filipwlodarczyk.SpringSecurity.registration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;
import pl.filipwlodarczyk.SpringSecurity.model.AppUser;
import pl.filipwlodarczyk.SpringSecurity.model.RegistrationRequest;
import pl.filipwlodarczyk.SpringSecurity.service.RegistrationService;

@RestController
@RequestMapping(path = "api/registration")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody RegistrationRequest request) throws Exception {
       return  registrationService.register(request);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}
