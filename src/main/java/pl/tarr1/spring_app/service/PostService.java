package pl.tarr1.spring_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.tarr1.spring_app.model.Post;
import pl.tarr1.spring_app.model.User;
import pl.tarr1.spring_app.model.enums.Category;
import pl.tarr1.spring_app.repository.PostRepository;
import pl.tarr1.spring_app.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.*;

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
    public Post addPostByUser(String title, String content, Category category, Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);   // SELECT * FROM user WHERE user_id = ?
        if (userOpt.isPresent()) {
            Post post = new Post(title, content, category, userOpt.get(), LocalDateTime.now());
            postRepository.save(post);                              // INSERT INTO post VALUES (?,?,?,?)
            return post;
        }
        return null;
    }

    // RZAD I. wyszukiwanie posta po kluczu głównym
    public Post findPostById(Long postId) {
        if (postRepository.findById(postId).isPresent()) {
            return postRepository.findById(postId).get();   // SELECT * FROM post WHERE post_id = ?
        }
        return null;
    }

    // RZAD II. usuwanie posta
    public boolean deletePostById(Long postId) {
        if (postRepository.findById(postId).isPresent()) {
            postRepository.deleteById(postId);      // DELETE FROM post WHERE post_id = ?
            return true;
        }
        return false;
    }

    // RZAD III. zmiana danych posta
    public Post updatePost(Long postId, String title, String content, Category category) {
        if (postRepository.findById(postId).isPresent()) {
            Post post = postRepository.findById(postId).get();
            post.setTitle(title);
            post.setContent(content);
            post.setCategory(category);
            postRepository.save(post);             // UPDATE post SET title = ?, content = ?, category = ? WHERE post_id = ?
            return post;
        }
        return null;
    }

    // Pobranie wszystkich postów bloaga posortowanych po dacie dodania malejąco
    public List<Post> getAllPostsOrderBySubmissionDateDesc() {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "submissionDate"));
    }

    // Zwróć posty danej kategorii posortowane po dacie dodania
    public List<Post> getPostsByCategoryOrderBySubmissionDateDesc(Category category) {
        return postRepository.findAllByCategoryOrderBySubmissionDateDesc(category);
    }

    // wynika zapytania w adnotacji @Query
    public Map<String, Integer> groupPostsIntoCategory() {
        Map<String, Integer> groupedMap = new HashMap<>();
        for (Object[] groupedObjects : postRepository.groupPostsIntoCategory()) {
            groupedMap.put(Category.values()[(Integer) groupedObjects[0]].name(),(Integer)groupedObjects[1]);
        }
        return groupedMap;
    }
}

