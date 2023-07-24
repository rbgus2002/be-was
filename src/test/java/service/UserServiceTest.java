package service;

import db.Database;
import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        UserService.addUser(user);

        // then
        assertEquals(user, Database.findUserById(userId));
    }
}
