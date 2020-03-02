package pl.tarr1.spring_app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.tarr1.spring_app.model.enums.Role;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity         // mapowanie klasy na encję db
public class User {
    @Id                                                 // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY - auto inkrementacja w tabeli, AUTO - auto inkrementacja globalna
    private Long userId;
    private String name;
//    @Column(name = "last_name")
    private String lastName;
    private String email;
    private String password;
    private LocalDateTime registrationDate;
    private Boolean status;
    @Enumerated
    private Role role;

    public User(String name, String lastName, String email, String password, LocalDateTime registrationDate, Boolean status, Role role) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.registrationDate = registrationDate;
        this.status = status;
        this.role = role;
    }
}
