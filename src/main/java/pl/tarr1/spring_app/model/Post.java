package pl.tarr1.spring_app.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import pl.tarr1.spring_app.model.enums.Category;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    @NotBlank(message = "Title is mandatory field")
    private String title;
    @NotBlank(message = "Content is mandatory field")
    @Type(type = "text")    // rozszerza typ varchar(255) dla string na text -> 4G
    private String content;
    @Enumerated
    private Category category;
    private LocalDateTime submissionDate;

    // każdy post ma dokładnie jednego autora -> User
    @ManyToOne
    private User user;
    // ==============================================
    public Post(String title, String content, Category category, User user, LocalDateTime submissionDate) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.user = user;
        this.submissionDate = submissionDate;
    }

}
