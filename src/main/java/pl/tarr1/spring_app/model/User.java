package pl.tarr1.spring_app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity         // mapowanie klasy na encję db
public class User {
    @Id                                                 // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY - auto inkrementacja w tabeli, AUTO - auto inkrementacja globalna
    private Long userId;
    @NotBlank(message = "name is mandatory field")
    private String name;
//    @Column(name = "last_name")
    @NotBlank(message = "last name is mandatory field")
    private String lastName;
    @Email(message = "email is incorrect")
    @NotBlank(message = "email is mandatory field")
    private String email;
    @Size(min = 6, message = "password must have at least {min} characters")
    @NotBlank(message = "password is mandatory field")
    private String password;
    @Transient                              // adnotacja hibernate wykluczająca pole z mapowania obiektowo-relacyjnego
    private String passwordConfirm;


    private LocalDateTime registrationDate;
    private Boolean status;
//    @Enumerated
//    private Role role;
    // ----------------------------------------------------------
    // każdy użytkownik może mieć wiele różnych ról
    // każda rola może być współdzielona przez wielu użytkowników
    @ManyToMany
    @JoinTable(     // tworzy tabelkę dla relacji n : m
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    Set<Role> roles = new HashSet<>();
    // ----------------------------------------------------------

    // użytkownik może opublikować wiele postów zapisanych w liście
    @JsonIgnore     // zignoruj to pole w publikowanym API
    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();
    // =================================================

    public User(String name, String lastName, String email, String password, LocalDateTime registrationDate, Boolean status, Role role) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.registrationDate = registrationDate;
        this.status = status;
        this.roles.add(role);
    }
}
