package pl.filipwlodarczyk.SpringSecurity.model;

import javax.persistence.*;

@Entity
public class Post {
    @Id
    @SequenceGenerator(name = "postId_sequence", sequenceName = "postId_sequence", allocationSize = 1)
    @GeneratedValue(generator = "postId_sequence", strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private AppUser appUser;
    private String content;

}
