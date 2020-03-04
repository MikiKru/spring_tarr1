package pl.tarr1.spring_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tarr1.spring_app.model.Post;
import pl.tarr1.spring_app.model.User;
import pl.tarr1.spring_app.model.enums.Category;
import pl.tarr1.spring_app.repository.PostRepository;
import pl.tarr1.spring_app.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PostService {
    private PostRepository postRepository;
    private UserRepository userRepository;
    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }
    // 1. dodawanie posta przez użytkownika
    public Post addPostByUser(String title, String content, Category category, Long userId){
        Optional<User> userOpt = userRepository.findById(userId);   // SELECT * FROM user WHERE user_id = ?
        if(userOpt.isPresent()){
            Post post = new Post(title, content, category, userOpt.get(), LocalDateTime.now());
            postRepository.save(post);                              // INSERT INTO post VALUES (?,?,?,?)
            return post;
        }
        return null;
    }
    // 2. wyszukiwanie posta po kluczu głównym
    public Optional<Post> findPostById(Long postId){
        return null;
    }
    // 3. usuwanie posta
    public boolean deletePostById(Long postId){
        return false;
    }
    // 4. zmiana danych posta
    public Post updatePost(Long postId, String title, String content, Category category){
        return null;
    }
}

