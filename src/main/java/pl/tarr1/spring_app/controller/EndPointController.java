package pl.tarr1.spring_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.tarr1.spring_app.model.Post;
import pl.tarr1.spring_app.model.User;
import pl.tarr1.spring_app.model.enums.Category;
import pl.tarr1.spring_app.service.PostService;
import pl.tarr1.spring_app.service.UserService;

import java.util.List;
import java.util.Map;

// publikuje REST API -> Representative State Transfer
@RequestMapping("/api")     // prefix do zasobów kontrollera EndPointConttroller
@RestController     // mapowanie żądań prokołu html -> GET, POST, PUT, DELETE
public class EndPointController {
    private UserService userService;
    private PostService postService;
    @Autowired
    public EndPointController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
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
    @PostMapping("/addPost")
    public Post addPost(
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam Category category,
            @RequestParam Long userId
    ){
       return postService.addPostByUser(title, content, category, userId);
    }
    @GetMapping("/posts/{postId}")
    public Post getPostById(@PathVariable("postId") Long postId){
        return postService.findPostById(postId);
    }
    @DeleteMapping("/deletePost")
    public boolean deletePostById(@RequestParam("postId") Long postId){
        return postService.deletePostById(postId);
    }
    @PutMapping("/updatePost")
    public Post updatePost(@RequestParam Long postId,
                           @RequestParam String title,
                           @RequestParam String content,
                           @RequestParam Category category){
        return postService.updatePost(postId, title, content, category);
    }
    @GetMapping("/posts")
    public List<Post> getAllPostsOrderBySubmissionDateDesc(){
        return postService.getAllPostsOrderBySubmissionDateDesc();
    }
    @GetMapping("/postsByCategory/{category}")
    public List<Post> getPostsByCategoryOrderBySubmissionDateDesc(
            @PathVariable("category") Category category
    ){
        return postService.getPostsByCategoryOrderBySubmissionDateDesc(category);
    }
    @GetMapping("/groupPostsIntoCategory")
    public Map<String, String> groupPostsIntoCategory(){
        return postService.groupPostsIntoCategory();
    }

    @PutMapping("/updatePostTitle")
    public boolean updatePostTitle(@RequestParam("postId") Long postId, @RequestParam("title") String title){
        return postService.updatePostTitle(postId, title);
    }
}
