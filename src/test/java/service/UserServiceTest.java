package service;

import model.User;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static service.UserService.addUser;

class UserServiceTest {
    SoftAssertions s = new SoftAssertions();

    @Test
    @DisplayName("같은 id의 유저가 들어올 경우 false를 반환한다.")
    void checkUserServiceAdd() {
        User testUser1 = new User("test", "password", "테스트", "test@test.com");
        User testUser2 = new User("test", "pass", "testtttt", "test@gmail.com");
        User testUser3 = new User("test1", "password", "테스트", "test@test.com");

        s.assertThat(addUser(testUser1)).isEqualTo(true);
        s.assertThat(addUser(testUser2)).isEqualTo(false);
        s.assertThat(addUser(testUser1)).isEqualTo(false);
        s.assertThat(addUser(testUser3)).isEqualTo(true);
    }

}