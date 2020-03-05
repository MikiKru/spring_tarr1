package pl.tarr1.spring_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

// Wszystkie klasy z adnotacją @Controller lub @RestConttroller słóżą do mapowania żądań http
// @Controller jest podstawowym stereotypem springa - beanem - zarządzana przez Spring Context
// posiada automatyczne akcje nasłuchujące na określone żądania
@RequestMapping("/home")
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
    // w nawiasach {} osadzamy część zmienną ścieżki
    @GetMapping("/hello/name={name}&age={age}")
    public String hello(
            @PathVariable("name") String name,  // pobiera wartość z URL i przepisuje do obiektu
            @PathVariable("age") Integer age,
            Model model                         // obiekt do przekazywania obiektów pomiędzy Java a html
    ){
        System.out.println("name = " + name);
        System.out.println("age = " + age);
        model.addAttribute("name", name);     // dodanie parametru do obiektu model, który będzie widoczny w html
        model.addAttribute("age", age);       // model.addAttribute("nazwa obiektu w html", nazwaObiektu);
        return "hello";
    }

}
