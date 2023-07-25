package db;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserRepository Test")
class UserRepositoryTest {
    UserRepository userRepository;

    @BeforeEach
    void init() {
        userRepository = new UserRepository();
    }

    @DisplayName("유저 save, findUserById 테스트 - 정상 처리")
    @Test
    void saveTest() {
        String userId = "id";
        User user = new User(userId, "password", "name", "email");

        userRepository.save(user);
        User findUser = userRepository.findUserById(userId).get();

        assertEquals(user, findUser);
    }

    @DisplayName("동일한 아이디로 등록된 회원이 있다면, 회원 가입 실패")
    @Test
    void saveFailTest() {
        String userId = "id";
        User user1 = new User(userId, "password", "name", "email");
        User user2 = new User(userId, "password", "name", "email");
        userRepository.save(user1);

        assertThatThrownBy(() -> userRepository.save(user2))
                .isInstanceOf(RuntimeException.class)
                .hasMessage(UserRepository.DUPLICATE_PRIMARY_KEY);
    }

    @DisplayName("모든 회원 조회 테스트")
    @Test
    void findAllTest() {
        User user1 = new User("userId1", "password", "name", "email");
        User user2 = new User("userId2", "password", "name", "email");
        userRepository.save(user1);
        userRepository.save(user2);

        Collection<User> users = userRepository.findAll();

        assertThat(users).containsExactly(user1, user2);
    }

    @DisplayName("회원 아이디로 회원 삭제 테스트")
    @Test
    void deleteTest() {
        String userId = "userId";
        User user = new User(userId, "password", "name", "email");
        userRepository.save(user);

        userRepository.deleteUserById(userId);

        assertTrue(userRepository.findUserById(userId).isEmpty());
    }

    @DisplayName("특정 userId로 저장된 회원이 없다면 삭제할 수 없음.")
    @Test
    void deleteFailTest() {
        String userId = "userId";

        assertThatThrownBy(() -> userRepository.deleteUserById(userId))
                .isInstanceOf(RuntimeException.class)
                .hasMessage(UserRepository.NO_SUCH_PRIMARY_KEY);
    }
}
