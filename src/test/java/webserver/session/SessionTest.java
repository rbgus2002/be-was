package webserver.session;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SessionTest {
    @DisplayName("User가 등록되지 않은 세션은 유효하지 않은 세션")
    @Test
    void tempSession() {
        Session session = new Session("123");

        assertFalse(session.isValid());
    }

    @DisplayName("User가 등록된 세션은 유효한 세션")
    @Test
    void validSession() {
        Session session = new Session("123");
        User user = new User("userId", "password", "name", "email");

        session.setUser(user);

        assertTrue(session.isValid());
    }
}