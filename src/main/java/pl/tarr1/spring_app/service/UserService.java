package pl.tarr1.spring_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.tarr1.spring_app.model.User;
import pl.tarr1.spring_app.repository.RoleRepository;
import pl.tarr1.spring_app.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service             // klasa implementująca logikę biznesową
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    @Autowired      // wstrzyknięcie zależności UserRepository z SpringContext do klasy UserService
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    // INSERT INTO user VALUES (?,?,?,?)
    public boolean registerUser(String name, String lastName, String email, String password){
        if(!getUserByEmail(email).isPresent()) {
            // String BCryptPasswordEncoder().encode(String) -> zwraca zaszyfrowane hasło
            userRepository.save(new User(name, lastName, email, new BCryptPasswordEncoder().encode(password),
                    LocalDateTime.now(), true, roleRepository.getOne(1L)));
            return true;
        }
        return false;
    }
    // SELECT * FROM user
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    // SELECT * FROM user WHERE user_id = ?
    public Optional<User> getUserById(Long userId){
        return userRepository.findById(userId);
    }
    // SELECT * FROM user WHERE email = ?
    public Optional<User> getUserByEmail(String email){
        // Optional.ofNullable(Object o) -> przyjmuje wartość lub null do Optionala
        // Optional.of(Object o) -> tworzy optionala tylko dla wartości nie null
        Optional<User> userOpt = Optional.ofNullable(userRepository.findFirstByEmail(email));
        return userOpt;
    }
    public String setNewPassword(Long userId, String newPassword, String confirmPassword){
        if(newPassword.equals(confirmPassword)) {
            // SELECT * FROM user WHERE user_id = ?
            if (getUserById(userId).isPresent()) {
                User userToUpdate = getUserById(userId).get();
                // UPDATE user SET password = ? WHERE user_id = ?
                userToUpdate.setPassword(newPassword);
                userRepository.save(userToUpdate);
                return userToUpdate.toString();
            }
            return "Nie ma takiego użytkownika";
        }
        return "Podane hasła muszą być takie same";
    }
    public boolean deleteUserById(Long userId){
        if(userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }
    // na bazie danych logowania i obiektu Authantication zwróć obiekt zalogowanego usera
    public User getUserBasedOnAuthentication(Authentication authentication){
        if(authentication == null){
            // gdy nie jesteśmy zalogowani
            return null;
        }
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        System.out.println(principal.getUsername());
        System.out.println(principal.getPassword());
        principal.getAuthorities().stream().forEach(o -> System.out.println(((GrantedAuthority) o).getAuthority()));
        // obiekt zalogowanego użytkownika
        return getUserByEmail(principal.getUsername()).get();
    }
    public boolean isUser(Authentication authentication){
        if(authentication == null){
            return false;
        }
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        return principal.getAuthorities()
                .stream()
                .anyMatch(o -> ((GrantedAuthority) o).getAuthority().equals("ROLE_USER"));
    }
    public boolean isAdmin(Authentication authentication){
        if(authentication == null){
            return false;
        }
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        return principal.getAuthorities()
                .stream()
                .anyMatch(o -> ((GrantedAuthority) o).getAuthority().equals("ROLE_ADMIN"));
    }
}
