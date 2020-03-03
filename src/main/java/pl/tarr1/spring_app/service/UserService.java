package pl.tarr1.spring_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tarr1.spring_app.model.User;
import pl.tarr1.spring_app.model.enums.Role;
import pl.tarr1.spring_app.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service             // klasa implementująca logikę biznesową
public class UserService {
    private UserRepository userRepository;
    @Autowired      // wstrzyknięcie zależności UserRepository z SpringContext do klasy UserService
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    // INSERT INTO user VALUES (?,?,?,?)
    public boolean registerUser(String name, String lastName, String email, String password){
        if(!getUserByEmail(email).isPresent()) {
            userRepository.save(new User(name, lastName, email, password,
                    LocalDateTime.now(), true, Role.ROLE_USER));
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






}
