package db;

import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class DatabaseTest {

    @AfterEach
    void tearDown() {
        Database.clear();
    }

    @Test
    @DisplayName("사용자를 찾으면 Optional로 반환한다.")
    void findUserByIdOptional() {
        //given
        String userId = "a";
        User user = new User(userId, "1234", "han", "a@a.com");
        Database.addUser(user);

        //when
        Optional<User> findUser = Database.findUserById(userId);

        //then
        assertThat(findUser).usingRecursiveComparison().isEqualTo(Optional.of(user));
    }
}