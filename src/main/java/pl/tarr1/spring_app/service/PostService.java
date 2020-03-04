package pl.tarr1.spring_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tarr1.spring_app.repository.PostRepository;
import pl.tarr1.spring_app.repository.UserRepository;

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

    // 2. wyszukiwanie posta po kluczu głównym

    // 3. usuwanie posta

    // 4. zmiana danych posta

}
