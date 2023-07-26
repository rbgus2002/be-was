package db;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTest {

    @Test
    @DisplayName("userId와 password를 통해 DB에 잘 저장되어 있는지 확인할 수 있다.")
    void db_validateUser() {
        //given
        String userId = "bigsand123";
        String password = "123";
        String name = "bigsand";
        String email = "bigsand@naver.com";
        User user = new User(userId, password, name, email);
        Database.addUser(user);

        //when,then
        assertTrue(Database.validateUser(userId,password));
    }

}