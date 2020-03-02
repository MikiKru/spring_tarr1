package pl.tarr1.spring_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tarr1.spring_app.model.User;
import pl.tarr1.spring_app.model.enums.Role;
import pl.tarr1.spring_app.repository.UserRepository;

import java.time.LocalDateTime;

@Service             // klasa implementująca logikę biznesową
public class UserService {
    private UserRepository userRepository;
    @Autowired      // wstrzyknięcie zależności UserRepository z SpringContext do klasy UserService
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public void registerUser(String name, String lastName, String email, String password){
        userRepository.save(new User(name, lastName, email,password,
                LocalDateTime.now(), true, Role.ROLE_USER));
    }
}
