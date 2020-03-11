package pl.tarr1.spring_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.tarr1.spring_app.model.Post;
import pl.tarr1.spring_app.model.User;
import pl.tarr1.spring_app.model.enums.Category;
import pl.tarr1.spring_app.service.PostService;
import pl.tarr1.spring_app.service.UserService;

import javax.validation.Valid;

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
    public String getPosts(Model model, Authentication authentication){
        // Model model - obiekt do komunikacji front-end i back-end
        // model.addAttribute("nazwaObiektuNaFroncie", obiektJava);
        model.addAttribute("posts", postService.getAllPostsOrderBySubmissionDateDesc());
        model.addAttribute("authentication", authentication);
        model.addAttribute("user", userService.getUserBasedOnAuthentication(authentication));
        model.addAttribute("isUser", userService.isUser(authentication));
        model.addAttribute("isAdmin", userService.isAdmin(authentication));
        return "posts";         // nazwa widoku bez .html
    }
    @GetMapping("/posts&{postId}")
    public String getPostById(@PathVariable("postId") Long postId, Model model, Authentication authentication){
        model.addAttribute("post", postService.findPostById(postId));
        model.addAttribute("authentication", authentication);
        model.addAttribute("user", userService.getUserBasedOnAuthentication(authentication));
        return "post";
    }
    @GetMapping("/addPost")                 // wywołanie formularza i przekazanie parametrów POST /addPost
    public String addPost(Model model, Authentication authentication){
        model.addAttribute("post", new Post());                 // przekazanie obiektu post do th:object formularza
        model.addAttribute("categories", Category.values());    // przekazanie tablicy kategorii do option formularza
        model.addAttribute("authentication", authentication);
        model.addAttribute("user", userService.getUserBasedOnAuthentication(authentication));
        return "addPost";
    }
    @PostMapping("/addPost")               // adres na którym odbierane są parametry przekazane żądaniem GET /addPost
    public String addPost(
            @ModelAttribute @Valid Post post,
            BindingResult bindingResult,
            Model model,
            Authentication authentication){
        // @ModelAttribute Typ nazwaObiektu -> pobiera obiekt przekazany określonym żądaniem
        if(bindingResult.hasErrors()){      // obiekt bindingResult przechowuje informacje o błędach
                                            // wynikających z niespełnienia wymagań adnotacji w modelu Post
            model.addAttribute("categories", Category.values());
            return "addPost";
        }
        postService.addPostByUser(
                post.getTitle(),
                post.getContent(),
                post.getCategory(),
                //userService.getUserById(3L).get().getUserId()
                userService.getUserBasedOnAuthentication(authentication).getUserId()
        );
        return "redirect:/";        // wykonanie żądania get na adres "/"
    }
    @GetMapping("/register")
    public String register(Model model, Authentication authentication){
        model.addAttribute("registerUser", new User());
        model.addAttribute("authentication", authentication);
        model.addAttribute("user", userService.getUserBasedOnAuthentication(authentication));
        return "registration";
    }
    @PostMapping("/register")
    public String register(@ModelAttribute("registerUser") @Valid User user, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(System.out::println);
            return "registration";
        }
        if(!user.getPassword().equals(user.getPasswordConfirm())){
            model.addAttribute("differentPassword", "Registration failed! Passwords were different.");
            return "registration";
        }
        if(!userService.registerUser(user.getName(),user.getLastName() ,user.getEmail() ,user.getPassword() )){
            model.addAttribute("emailExists", "E-mail " + user.getEmail()+ " exists in our blog!");
            return "registration";
        }
        return "redirect:/";
    }
    @GetMapping("/login")
    public String login(Authentication authentication, Model model){
        model.addAttribute("authentication", authentication);
        model.addAttribute("user", userService.getUserBasedOnAuthentication(authentication));
        return "login";
    }
    @GetMapping("/contact")
    public String contact(Authentication authentication, Model model){
        model.addAttribute("authentication", authentication);
        model.addAttribute("user", userService.getUserBasedOnAuthentication(authentication));
        return "contact";
    }
}
