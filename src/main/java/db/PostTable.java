package db;

import model.Post;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PostTable {
    private static final Map<Long, Post> posts = new ConcurrentHashMap<>();

    public static void addPost(Post post) {
        posts.put(post.getPostId(), post);
    }

    public static Post findByPostId(Long postId) {
        return posts.get(postId);
    }

    public static Collection<Post> findAll() {
        return posts.values();
    }
}
