package pl.tarr1.spring_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.tarr1.spring_app.model.enums.Role;
import pl.tarr1.spring_app.service.UserService;

import java.time.LocalDateTime;

// publikuje REST API -> Representative State Transfer
@RestController     // mapowanie żądań prokołu html -> GET, POST, PUT, DELETE
public class EndPointController {
    private UserService userService;
    @Autowired
    public EndPointController(UserService userService) {
        this.userService = userService;
    }
    // metoda do rejestracji użytkowników
    @PostMapping("/registration")
    public void registerUser(
            String name, String lastName, String email, String password
    ){
        // logika biznesowa zapisana w klasie UserService
        userService.registerUser(name, lastName, email, password);
    }
}
