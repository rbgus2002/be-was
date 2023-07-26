package db;

import model.User;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static db.Database.*;
import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    private SoftAssertions softAssertions;

    @BeforeEach
    void setup() {
        clear();
        softAssertions = new SoftAssertions();
    }

    @AfterEach
    void after() {
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("유저 정보를 데이터베이스에 추가할 수 있어야 한다")
    void addUserTest() {
        // Given
        User user1 = new User("kimahhh", "aa", "김아현", "kimahyunn132@gmail.com");

        // When
        addUser(user1);

        // Then
        softAssertions.assertThat(findAll().size())
                .as("유저가 추가되지 않았습니다.\n현재 userDB size: %d", findAll().size())
                .isEqualTo(1);

        // Given
        User user2 = new User("gildong", "dd", "홍길동", "gildong@naver.com");

        // When
        addUser(user2);

        // Then
        softAssertions.assertThat(findAll().size())
                .as("유저가 추가되지 않았습니다.\n현재 userDB size: %d", findAll().size())
                .isEqualTo(2);
    }

    @Test
    @DisplayName("유저 아이디로 유저 정보를 조회할 수 있어야 한다")
    void findUserByIdTest() {
        // Given
        User user1 = new User("kimahhh", "aa", "김아현", "kimahyunn132@gmail.com");
        User user2 = new User("gildong", "dd", "홍길동", "gildong@naver.com");
        addUser(user1);
        addUser(user2);

        // When
        User findUser1 = findUserById("kimahhh");
        User findUser2 = findUserById("gildong");

        // Then
        softAssertions.assertThat(findUser1)
                .as("유저 아이디로 유저를 조회할 수 없습니다.\n현재 값: %s", findUser1.toString())
                .isEqualTo(user1);
        softAssertions.assertThat(findUser2)
                .as("유저 아이디로 유저를 조회할 수 없습니다.\n현재 값: %s", findUser2.toString())
                .isEqualTo(user2);
    }

    @Test
    @DisplayName("모든 유저 정보를 조회할 수 있어야 한다")
    void findAllTest() {
        // Given
        User user1 = new User("kimahhh", "aa", "김아현", "kimahyunn132@gmail.com");
        User user2 = new User("gildong", "dd", "홍길동", "gildong@naver.com");
        User user3 = new User("mynameisK", "kk", "kkKkk", "kkkkk@daum.net");
        addUser(user1);
        addUser(user2);
        addUser(user3);

        // When
        Collection<User> allUsers = findAll();

        // Then
        softAssertions.assertThat(allUsers.contains(user1))
                .as("user1이 포함되어 있지 않습니다.\n현재 값: %s", allUsers.contains(user1))
                .isEqualTo(true);
        softAssertions.assertThat(allUsers.contains(user2))
                .as("user2가 포함되어 있지 않습니다.\n현재 값: %s", allUsers.contains(user2))
                .isEqualTo(true);
        softAssertions.assertThat(allUsers.contains(user3))
                .as("user3이 포함되어 있지 않습니다.\n현재 값: %s", allUsers.contains(user3))
                .isEqualTo(true);
    }

    @Test
    @DisplayName("유저 정보를 초기화할 수 있어야 한다")
    void clearTest() {
        // Given
        User user1 = new User("kimahhh", "aa", "김아현", "kimahyunn132@gmail.com");
        User user2 = new User("gildong", "dd", "홍길동", "gildong@naver.com");
        User user3 = new User("mynameisK", "kk", "kkKkk", "kkkkk@daum.net");
        addUser(user1);
        addUser(user2);
        addUser(user3);

        // When
        clear();

        // Then
        softAssertions.assertThat(findAll().size())
                .as("User DataBase가 초기화되지 않았습니다.\n현재 userDB size: %s", findAll().size())
                .isEqualTo(0);
    }

}