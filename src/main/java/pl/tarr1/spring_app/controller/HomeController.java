package pl.tarr1.spring_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Wszystkie klasy z adnotacją @Controller lub @RestConttroller słóżą do mapowania żądań http
// @Controller jest podstawowym stereotypem springa - beanem - zarządzana przez Spring Context
// posiada automatyczne akcje nasłuchujące na określone żądania
@Controller
public class HomeController {

    @GetMapping("/")        // mapuje na adres domowy "/" metodą GET -> czyli po dopasowaniu adres wywołuje metodę poniżej
    public String home(){
        return "home";       // w return zwracamy nazwę szablonu html (Thymeleaf) - bez .html
    }
    @GetMapping("/about")
    public String about(){
        return "about";
    }
}
