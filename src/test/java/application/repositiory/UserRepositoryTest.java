package application.repositiory;

import application.dto.UserDto;
import exception.InvalidPathException;
import exception.InvalidQueryParameterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository = UserRepository.USER_REPOSITORY;
    }

    @Test
    @DisplayName("유저가 repository 에 정상적으로 추가되어야 한다.")
    void createUser() {
        String userId = "1";
        String password = "2468";
        String name = "LBC";
        String email = "wpdltm5@naver.com";

        UserDto userDto = new UserDto.Builder()
                .withUserId(userId)
                .withPassword(password)
                .withName(name)
                .withEmail(email)
                .build();

        userRepository.addUser(userDto);
        assertEquals(userDto, userRepository.findUserById("1"));
        assertThrows(InvalidQueryParameterException.class, () -> userRepository.findUserById("2"));
    }

    @Test
    void findAll() {
        String userId = "1";
        String password = "2468";
        String name = "LBC";
        String email = "wpdltm5@naver.com";

        UserDto userDto = new UserDto.Builder()
                .withUserId(userId)
                .withPassword(password)
                .withName(name)
                .withEmail(email)
                .build();

        userRepository.addUser(userDto);
        assertEquals(List.of(userDto), userRepository.findAll());
    }
}