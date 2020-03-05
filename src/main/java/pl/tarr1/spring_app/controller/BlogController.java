package pl.tarr1.spring_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BlogController {

    @GetMapping("/")
    public String getPosts(){
        return "posts";         // nazwa widoku bez .html
    }
}
