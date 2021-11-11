package pl.filipwlodarczyk.SpringSecurity.model;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
    private String name;
    private String username;
    private String password;
    private String email;
}
