package service;

import domain.Post;
import domain.User;
import repository.PostRepository;

import java.time.LocalDateTime;
import java.util.List;

public class PostService {

    private PostService() {}

    public static Post createPost(User user, String title, String contents) {
        validatePostParameters(user, title, contents);

        Post post = new Post(user, title, contents, LocalDateTime.now());
        PostRepository.addPost(post);
        return post;
    }

    public static List<Post> getAllPosts() {
        return PostRepository.findAll();
    }

    public static Post findPost(String postId) {
        return PostRepository.findPostById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 게시글이 없습니다."));
    }

    public static Post deletePost(String postId) {
        Post post = PostRepository.deletePost(postId);
        if (post == null) {
            throw new IllegalArgumentException("해당 게시글이 없습니다.");
        }

        return post;
    }

    private static void validatePostParameters(User user, String... parameters) {
        if (user == null) {
            throw new IllegalArgumentException("사용자 정보가 없습니다.");
        }

        for (String parameter : parameters) {
            if (parameter == null || parameter.isEmpty()) {
                throw new IllegalArgumentException("게시글 정보가 비어있거나 없습니다.");
            }
        }
    }
}
