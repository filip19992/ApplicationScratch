package pl.filipwlodarczyk.SpringSecurity.registration.token;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationToken {

    @Id
    @SequenceGenerator(name = "tokenId_sequence", sequenceName = "tokenId_sequence", allocationSize = 1)
    @GeneratedValue(generator = "tokenId_sequence", strategy = IDENTITY)
    private Long id;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private LocalDateTime confirmedAt;
}
