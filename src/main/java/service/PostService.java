package service;

import db.Database;
import model.Post;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class PostService {
    private static final String POST_WRITER = "writer";
    private static final String POST_TITLE = "title";
    private static final String POST_CONTENT = "contents";

    public static Post addPost(Map<String, String> parameterMap) {
        Post post = new Post(parameterMap.get(POST_WRITER), parameterMap.get(POST_TITLE), parameterMap.get(POST_CONTENT));
        Database.addPost(post);
        return post;
    }

    public static Post getPostByPostId(int postId) {
        return Database.findPostByPostId(postId);
    }

    public static Collection<Post> getAllPostByUserId(String userId) {
        Collection<Post> postList = Database.findAllPost();
        return postList.stream()
                .filter(post -> post.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public static Collection<Post> getAllPost() {
        return Database.findAllPost();
    }

    public static void updatePost(int postId, Post post) {
        Database.updatePost(postId, post);
    }
}
