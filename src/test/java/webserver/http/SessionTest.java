package webserver.http;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SessionTest {
    @Test
    @DisplayName("put으로 key, value쌍을 storage에 저장하고, get으로 key에 해당하는 value를 가져온다.")
    void sessionTest() {
        // given
        String sessionKey = UUID.randomUUID().toString();
        User user = new User("userId", "password", "name", "email");

        // when
        Session.put(sessionKey, user);

        // then
        assertEquals(user, Session.get(sessionKey));
    }
}
