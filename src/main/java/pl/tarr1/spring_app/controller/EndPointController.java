package pl.tarr1.spring_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.tarr1.spring_app.model.User;
import pl.tarr1.spring_app.model.enums.Role;
import pl.tarr1.spring_app.service.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public boolean registerUser(
            String name, String lastName, String email, String password
    ){
        // logika biznesowa zapisana w klasie UserService
        return userService.registerUser(name, lastName, email, password);
    }
    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
    @GetMapping("/users/{userId}")   // w {} występuje część zmienna ścieżki
    public User getUserById(@PathVariable("userId") Long userId){
        // sprawdzam czy optional nie zawiera null
        return userService.getUserById(userId).orElse(null);
    }
    @GetMapping("/userByEmail")   // w {} występuje część zmienna ścieżki
    public User getUserByEmail(@RequestParam("email") String email){    // @RequestParam -> przekazuje dane w parametrach protokołu html
        return userService.getUserByEmail(email).orElse(null);
    }
    @PutMapping("/changePassword")
    public String setNewPassword(
            @RequestParam("userId") Long userId,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword
    ){
        return userService.setNewPassword(userId, newPassword, confirmPassword);
    }
    @DeleteMapping("/deleteUser")
    public boolean deleteUserById(@RequestParam("userId") Long userId){
        return userService.deleteUserById(userId);
    }

}
