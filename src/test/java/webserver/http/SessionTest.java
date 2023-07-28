package webserver.http;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SessionTest {
    @Test
    @DisplayName("put으로 key, value쌍을 storage에 저장하고, get으로 key에 해당하는 value를 가져온다.")
    void sessionTest() {
        // given
        User user = new User("userId", "password", "name", "email");

        // when
        String sessionKey = Session.addSession(user);

        // then
        assertEquals(user, Session.get(sessionKey));
    }
}
