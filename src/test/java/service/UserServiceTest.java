package service;

import db.Database;
import db.SessionStorage;
import exception.NotExistUserException;
import exception.SessionIdException;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static db.Database.clear;
import static db.SessionStorage.*;
import static exception.ExceptionList.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    UserService userService;
    SoftAssertions softAssertions;

    @BeforeEach
    void setup() {
        userService = new UserService();
        softAssertions = new SoftAssertions();
        clear();
        clearSessionIds();
    }

    @AfterEach
    void after() {
        softAssertions.assertAll();
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
        softAssertions.assertThat(exception.getMessage())
                .as("적절한 오류가 던져지지 않습니다.\n현재 값: %s", exception.getMessage())
                .isEqualTo(ALREADY_EXIST_USER);
    }

    @Test
    @DisplayName("잘못된 정보로 로그인 시 오류를 반환해야 한다")
    void loginUsingWrongInformation() {
        // Given
        Map<String, String> user1 = new HashMap<>();
        user1.put("userId", "honggildong");
        user1.put("password", "1234");
        user1.put("name", "홍길동");
        user1.put("email", "hgd@gmail.com");
        userService.createUser(user1);

        // When
        Map<String, String> loginInformation = new HashMap<>();
        loginInformation.put("userId", "honggildong");
        loginInformation.put("password", "0000");
        Exception exception = assertThrows(NotExistUserException.class, () -> {
            userService.loginUser(loginInformation);
        });

        // Then
        softAssertions.assertThat(exception.getMessage())
                .as("적절한 오류가 던져지지 않습니다.\n현재 값: %s", exception.getMessage())
                .isEqualTo(NOT_EXIST_USER);
    }

    @Test
    @DisplayName("로그인 시 세션 아이디를 랜덤으로 생성하여 SessionStorage에 세션 아이디와 유저 아이디 쌍을 저장해야 한다")
    void putSessionIdWhenLogin() {
        // Given
        Map<String, String> user1 = new HashMap<>();
        user1.put("userId", "honggildong");
        user1.put("password", "1234");
        user1.put("name", "홍길동");
        user1.put("email", "hgd@gmail.com");
        userService.createUser(user1);

        // When
        Map<String, String> loginInformation = new HashMap<>();
        loginInformation.put("userId", "honggildong");
        loginInformation.put("password", "1234");
        String sessionId = userService.loginUser(loginInformation);

        // Then
        softAssertions.assertThat(SessionStorage.findAllSessionIds().size())
                .as("Database의 크기가 1이 아닙니다.\n현재 값: %d", SessionStorage.findAllSessionIds().size())
                .isEqualTo(1);
        softAssertions.assertThat(findUserIdBySessionId(sessionId))
                .as("userId가 'honggildong'이 아닙니다.\n현재 값: %s", findUserIdBySessionId(sessionId))
                .isEqualTo("honggildong");
    }

    @Test
    @DisplayName("존재하지 않는 세션 아이디로 로그아웃 시 NOT EXIST SESSION ID 예외를 던져야 한다")
    void notExistSessionIdException() {
        // Given
        String sessionId = "4bc504ae-a64f-4fba-a3df-4466c012915a";

        // When
        Exception exception = assertThrows(SessionIdException.class, () -> {
            userService.logoutUser(sessionId);
        });

        // Then
        softAssertions.assertThat(exception.getMessage())
                .as("적절한 오류가 던져지지 않습니다.\n현재 값: %s", exception.getMessage())
                .isEqualTo(NOT_EXIST_SESSION_ID);
    }

    @Test
    @DisplayName("로그아웃 시 세션 아이디를 삭제해야 한다")
    void deleteSessionIdWhenLogout() {
        // Given
        Map<String, String> user1 = new HashMap<>();
        user1.put("userId", "honggildong");
        user1.put("password", "1234");
        user1.put("name", "홍길동");
        user1.put("email", "hgd@gmail.com");
        userService.createUser(user1);

        Map<String, String> loginInformation = new HashMap<>();
        loginInformation.put("userId", "honggildong");
        loginInformation.put("password", "1234");
        String sessionId = userService.loginUser(loginInformation);

        // When
        userService.logoutUser(sessionId);

        // Then
        softAssertions.assertThat(findAllSessionIds().size())
                .as("세션 아이디가 삭제되지 않았습니다.\n현재 값: %s", findAllSessionIds().size())
                .isEqualTo(0);
    }

}