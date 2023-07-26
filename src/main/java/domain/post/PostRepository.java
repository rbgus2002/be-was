package domain.post;

import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PostRepository {
    private static int sequence = 0;

    private static Map<Integer, Post> posts = Maps.newHashMap();

    public static void addPost(Post post) {
        posts.put(sequence++, post);
    }

    public static Optional<Post> findPostById(int postId) {
        return Optional.ofNullable(posts.get(postId));
    }

    public static List<Post> findAll() {
        return new ArrayList<>(posts.values());
    }

}
