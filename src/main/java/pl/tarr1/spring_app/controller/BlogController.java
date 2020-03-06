package pl.tarr1.spring_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.tarr1.spring_app.service.PostService;
import pl.tarr1.spring_app.service.UserService;

@Controller
public class BlogController {
    private PostService postService;
    private UserService userService;
    @Autowired
    public BlogController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String getPosts(Model model){
        // Model model - obiekt do komunikacji front-end i back-end
        // model.addAttribute("nazwaObiektuNaFroncie", obiektJava);
        model.addAttribute("posts", postService.getAllPostsOrderBySubmissionDateDesc());
        return "posts";         // nazwa widoku bez .html
    }
    @GetMapping("/posts&{postId}")
    public String getPostById(@PathVariable("postId") Long postId, Model model){
        model.addAttribute("post", postService.findPostById(postId));
        return "post";
    }
    @GetMapping("/addPost")
    public String addPost(){
        return "addPost";
    }
    @GetMapping("/register")
    public String register(){
        return "registration";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
