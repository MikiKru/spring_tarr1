package pl.tarr1.spring_app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import pl.tarr1.spring_app.model.enums.Category;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    private String title;
    @Type(type = "text")    // rozszerza typ varchar(255) dla string na text -> 4G
    private String content;
    @Enumerated
    private Category category;
    private LocalDateTime submissionDate;

    // każdy post ma dokładnie jednego autora -> User
    @ManyToOne
    private User user;
    // ==============================================
}
