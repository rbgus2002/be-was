package service;

import db.UserRepository;
import model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class UserServiceTest {
    UserRepository userRepository;
    UserService userService;

    @BeforeEach
    void init() {
        userRepository = new UserRepository();
        userService = new UserService(userRepository);
    }

    @DisplayName("회원 가입 테스트 - 정상 처리")
    @Test
    void joinTest() {
        User user = new User("userId", "password", "name", "email");

        userService.join(user);

        assertThat(userRepository.findUserById(user.getUserId())).isPresent();
    }

    @DisplayName("이미 등록된 UserId인 경우 회원 가입 실패")
    @Test
    void joinFailTest() {
        String userId = "userId";
        User user1 = new User(userId, "password", "name", "email");
        userService.join(user1);
        User user2 = new User(userId, "password", "name", "email");

        assertThatThrownBy(() -> userService.join(user2))
                .isInstanceOf(RuntimeException.class)
                .hasMessage(UserService.DUPLICATED_ID);
    }
}