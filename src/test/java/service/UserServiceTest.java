package service;

import db.Database;
import dto.CreateUserRequestDto;
import dto.LoginRequestDto;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    @Test
    @DisplayName("User가 Database에 추가되어야 한다.")
    void addUser() {
        // given
        String userId = "userId";
        String password = "password";
        String name = "name";
        String email = "email";

        // when
        UserService.createUser(new CreateUserRequestDto(userId, password, name, email));

        // then
        User user = Database.findUserById(userId).get();
        assertAll(() -> {
            assertEquals(userId, user.getUserId());
            assertEquals(password, user.getPassword());
            assertEquals(name, user.getName());
            assertEquals(email, user.getEmail());
        });
    }

    @Test
    @DisplayName("id, password가 일치하는 User가 로그인하면 해당 유저 객체를 리턴한다.")
    void loginValid() {
        // given
        String userId = "userId";
        String password = "password";
        String name = "name";
        String email = "email";
        UserService.createUser(new CreateUserRequestDto(userId, password, name, email));

        // when
        Optional<User> loginUser = UserService.login(new LoginRequestDto(userId, password));

        // then
        User user = loginUser.get();
        assertAll(() -> {
            assertEquals(userId, user.getUserId());
            assertEquals(password, user.getPassword());
            assertEquals(name, user.getName());
            assertEquals(email, user.getEmail());
        });
    }

    @Test
    @DisplayName("password가 불일치하는 User가 로그인하면 Optional.empty를 리턴한다.")
    void loginWithInvalidPassword() {
        // given
        String userId = "userId";
        String password = "password";
        String name = "name";
        String email = "email";
        User user = new User(userId, password, name, email);
        UserService.createUser(new CreateUserRequestDto(userId, password, name, email));

        // when
        Optional<User> loginUser = UserService.login(new LoginRequestDto(userId, "password1"));

        // then
        assertEquals(Optional.empty(), loginUser);
    }

    @Test
    @DisplayName("존재하지 않는 id를 가진 User가 로그인하면 Optional.empty를 리턴한다.")
    void loginWithNoExistId() {
        // given
        String userId = "userId";
        String password = "password";
        String name = "name";
        String email = "email";
        User user = new User(userId, password, name, email);
        UserService.createUser(new CreateUserRequestDto(userId, password, name, email));

        // when
        Optional<User> loginUser = UserService.login(new LoginRequestDto("userId1", password));

        // then
        assertEquals(Optional.empty(), loginUser);
    }
}
