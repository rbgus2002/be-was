package service;

import db.Database;
import model.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.SoftAssertions;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static service.PostService.*;
import static org.assertj.core.api.Assertions.assertThat;

class PostServiceTest {
    @BeforeEach
    public void setup() {
        Database.deleteAllPost();
    }

    @Test
    @DisplayName("Post를 생성하면 DB에 저장된다.")
    public void addPost() {
        // Given
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put(POST_WRITER, "jst0951");
        parameterMap.put(POST_TITLE, "글");
        parameterMap.put(POST_CONTENT, "글내용");

        // When
        Post post = PostService.addPost(parameterMap);

        // Then
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(post.getUserId()).isEqualTo("jst0951");
        assertions.assertThat(post.getTitle()).isEqualTo("글");
        assertions.assertThat(post.getContent()).isEqualTo("글내용");
        assertions.assertAll();
    }

    @Test
    @DisplayName("postId로 Post를 찾을 수 있어야 한다.")
    public void getPostByPostId() {
        // Given
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put(POST_WRITER, "jst0951");
        parameterMap.put(POST_TITLE, "글");
        parameterMap.put(POST_CONTENT, "글내용");
        Post savedPost = PostService.addPost(parameterMap);

        // When
        Post foundPost = PostService.getPostByPostId(savedPost.getPostId());

        // Then
        assertThat(foundPost).isEqualTo(savedPost);
    }

    @Test
    @DisplayName("특정 userId의 모든 Post를 찾을 수 있어야 한다.")
    public void getAllPostByUserId() {
        // Given
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put(POST_WRITER, "jst0951");
        parameterMap.put(POST_TITLE, "글1");
        parameterMap.put(POST_CONTENT, "글내용1");
        Post post1 = PostService.addPost(parameterMap);

        parameterMap.put(POST_WRITER, "jst0082");
        parameterMap.put(POST_TITLE, "글2");
        parameterMap.put(POST_CONTENT, "글내용2");
        Post post2 = PostService.addPost(parameterMap);

        parameterMap.put(POST_WRITER, "jst0951");
        parameterMap.put(POST_TITLE, "글3");
        parameterMap.put(POST_CONTENT, "글내용3");
        Post post3 = PostService.addPost(parameterMap);

        // When
        Collection<Post> posts = PostService.getAllPostByUserId("jst0951");

        // Then
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(posts.contains(post1)).isTrue();
        assertions.assertThat(posts.contains(post2)).isFalse();
        assertions.assertThat(posts.contains(post3)).isTrue();
        assertions.assertAll();
    }

    @Test
    @DisplayName("모든 추가된 Post를 반환해야 한다.")
    public void getAllPost() {
        // Given
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put(POST_WRITER, "jst0951");
        parameterMap.put(POST_TITLE, "글1");
        parameterMap.put(POST_CONTENT, "글내용1");
        Post post1 = PostService.addPost(parameterMap);

        parameterMap.put(POST_WRITER, "jst0082");
        parameterMap.put(POST_TITLE, "글2");
        parameterMap.put(POST_CONTENT, "글내용2");
        Post post2 = PostService.addPost(parameterMap);

        // When
        Collection<Post> posts = PostService.getAllPost();

        // Then
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(posts.size()).isEqualTo(2);
        assertions.assertThat(posts.contains(post1)).isTrue();
        assertions.assertThat(posts.contains(post2)).isTrue();
        assertions.assertAll();
    }

    @Test
    @DisplayName("Post 갱신 후에 최신의 값이 반환되어야 한다.")
    public void updatePost() {
        // Given
        Map<String, String> parameterMap = new HashMap<>();
        parameterMap.put(POST_WRITER, "jst0951");
        parameterMap.put(POST_TITLE, "글1");
        parameterMap.put(POST_CONTENT, "글내용1");
        Post post = PostService.addPost(parameterMap);

        // When
        PostService.updatePost(post.getPostId(), new Post("jst0082", "글2", "글내용2"));
        Post updatedPost = PostService.getPostByPostId(post.getPostId());

        // Then
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(updatedPost.getPostId()).isEqualTo(post.getPostId());
        assertions.assertThat(updatedPost.getUserId()).isEqualTo("jst0082");
        assertions.assertThat(updatedPost.getTitle()).isEqualTo("글2");
        assertions.assertThat(updatedPost.getContent()).isEqualTo("글내용2");
        assertions.assertAll();
    }
}
