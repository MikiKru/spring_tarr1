package pl.tarr1.spring_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.tarr1.spring_app.service.UserService;

@Controller
public class ErrrorPageController implements ErrorController {
    private UserService userService;
    @Autowired
    public ErrrorPageController(UserService userService) {
        this.userService = userService;
    }
    @Override
    public String getErrorPath() {
        return "/error";
    }
    @GetMapping("/error")
    public String errorPage(Model model, Authentication authentication){
        model.addAttribute("authentication", authentication);
        model.addAttribute("user", userService.getUserBasedOnAuthentication(authentication));
        return "errorPage";
    }
    @GetMapping("/login_error")
    public String errorLogin(Model model, Authentication authentication){
        model.addAttribute("authentication", authentication);
        model.addAttribute("user", userService.getUserBasedOnAuthentication(authentication));
        return "errorPage";
    }
}
