package service;

import db.Database;
import model.Post;

import java.util.Collection;
import java.util.stream.Collectors;

public class PostService {
    public static Post addPost(String userId, String title, String content) {
        Post post = new Post(userId, title, content);
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
