package service;

import db.UserRepository;
import exception.UserServiceException;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;


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
                .hasMessage(UserServiceException.DUPLICATED_ID);
    }

    @DisplayName("로그인 성공 테스트")
    @Test
    void loginSuccessTest() {
        User user = new User("userId", "password", "name", "email");
        userService.join(user);

        User loginUser = userService.login(user.getUserId(), user.getPassword());

        assertEquals(user, loginUser);
    }

    @DisplayName("로그인 실패 테스트 - 아이디가 존재하지 않는 경우")
    @Test
    void loginFailTest1() {
        assertThatThrownBy(() -> userService.login("wrongId", "password"));
    }

    @DisplayName("로그인 실패 테스트 - 패스워드가 틀린 경우")
    @Test
    void loginFailTest2() {
        User user = new User("userId", "password", "name", "email");
        userService.join(user);

        assertThatThrownBy(() -> userService.login(user.getUserId(), "wrong password"));
    }
}