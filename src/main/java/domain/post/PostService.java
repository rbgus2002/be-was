package domain.post;

import domain.user.User;

import java.time.LocalDateTime;
import java.util.List;

public class PostService {

    private PostService() {}

    public static Post createPost(User user, String title, String contents) {
        Post post = new Post(user, title, contents, LocalDateTime.now());
        PostRepository.addPost(post);
        return post;
    }

    public static List<Post> getAllPosts() {
        return PostRepository.findAll();
    }

}
