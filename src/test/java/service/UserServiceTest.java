package service;

import db.Database;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static db.Database.clear;
import static exception.Exception.ALREADY_EXIST_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    UserService userService;

    @BeforeEach
    void setup() {
        userService = new UserService();
        clear();
    }

    @Test
    @DisplayName("User를 생성해서 DB에 저장할 수 있어야 한다")
    void createUserTest() {
        // Given
        Map<String, String> user1 = new HashMap<>();
        user1.put("userId", "honggildong");
        user1.put("password", "1234");
        user1.put("name", "홍길동");
        user1.put("email", "hgd@gmail.com");
        Map<String, String> user2 = new HashMap<>();
        user2.put("userId", "gogildong");
        user2.put("password", "0000");
        user2.put("name", "고길동");
        user2.put("email", "ggd@naver.com");

        // When
        userService.createUser(user1);

        // Then
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(Database.findAll().size())
                .as("Database의 크기가 1이 아닙니다.\n현재 값: %d", Database.findAll().size())
                .isEqualTo(1);
        softAssertions.assertThat(Database.findUserById("honggildong").getName())
                .as("Id로 이름을 찾을 수 없습니다.\n 현재 값: %s", Database.findUserById("honggildong").getName())
                .isEqualTo("홍길동");

        // When
        userService.createUser(user2);

        // Then
        softAssertions.assertThat(Database.findAll().size())
                .as("Database의 크기가 2가 아닙니다.\n현재 값: %d", Database.findAll().size())
                .isEqualTo(2);
        softAssertions.assertThat(Database.findUserById("gogildong").getName())
                .as("Id로 이름을 찾을 수 없습니다.\n 현재 값: %s", Database.findUserById("gogildong")
                        .getName()).isEqualTo("고길동");
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("중복된 아이디로 회원가입 시 오류를 반환해야 한다")
    void AlreadyExistUserTest() {
        // Given
        Map<String, String> user1 = new HashMap<>();
        user1.put("userId", "honggildong");
        user1.put("password", "1234");
        user1.put("name", "홍길동");
        user1.put("email", "hgd@gmail.com");
        Map<String, String> user2 = new HashMap<>();
        user2.put("userId", "honggildong");
        user2.put("password", "1234");
        user2.put("name", "홍길동");
        user2.put("email", "hgd@gmail.com");

        // When
        userService.createUser(user1);
        Exception exception = assertThrows(Exception.class, () -> {
            userService.createUser(user2);
        });

        // Then
        assertThat(exception.getMessage())
                .as("적절한 오류가 던져지지 않습니다.\n현재 값: %s", exception.getMessage())
                .isEqualTo(ALREADY_EXIST_USER);
    }

}