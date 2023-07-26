package repository;

import com.google.common.collect.Maps;
import domain.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PostRepository {
    private static int sequence = 0;

    private static Map<Integer, Post> posts = Maps.newHashMap();

    public static void addPost(Post post) {
        post.setPostId(String.valueOf(sequence));
        posts.put(sequence++, post);
    }

    public static Optional<Post> findPostById(String postId) {
        return Optional.ofNullable(posts.get(Integer.valueOf(postId)));
    }

    public static List<Post> findAll() {
        return new ArrayList<>(posts.values());
    }

    public static Post deletePost(String postId) {
        return posts.remove(Integer.valueOf(postId));
    }

}
