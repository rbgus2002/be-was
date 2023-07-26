package service;

import db.Database;
import dto.CreateUserRequestDto;
import dto.LoginRequestDto;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

        User user = new User(userId, password, name, email);

        // when
        UserService.createUser(new CreateUserRequestDto(userId, password, name, email));

        // then
        assertEquals(user, Database.findUserById(userId));
    }

    @Test
    @DisplayName("id, password가 일치하는 User가 로그인하면 true를 리턴한다.")
    void loginValid() {
        // given
        String userId = "userId";
        String password = "password";
        String name = "name";
        String email = "email";
        User user = new User(userId, password, name, email);
        UserService.createUser(new CreateUserRequestDto(userId, password, name, email));

        // when
        boolean isExistUser = UserService.login(new LoginRequestDto(userId, password));

        // then
        assertTrue(isExistUser);
    }

    @Test
    @DisplayName("password가 불일치하는 User가 로그인하면 false를 리턴한다.")
    void loginWithInvalidPassword() {
        // given
        String userId = "userId";
        String password = "password";
        String name = "name";
        String email = "email";
        User user = new User(userId, password, name, email);
        UserService.createUser(new CreateUserRequestDto(userId, password, name, email));

        // when
        boolean isExistUser = UserService.login(new LoginRequestDto(userId, "password1"));

        // then
        assertFalse(isExistUser);
    }

    @Test
    @DisplayName("존재하지 않는 id를 가진 User가 로그인하면 false를 리턴한다.")
    void loginWithNoExistId() {
        // given
        String userId = "userId";
        String password = "password";
        String name = "name";
        String email = "email";
        User user = new User(userId, password, name, email);
        UserService.createUser(new CreateUserRequestDto(userId, password, name, email));

        // when
        boolean isExistUser = UserService.login(new LoginRequestDto("userId1", password));

        // then
        assertFalse(isExistUser);
    }
}
