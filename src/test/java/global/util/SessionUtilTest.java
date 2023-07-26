package global.util;

import exception.UnauthorizedException;
import global.constant.Headers;
import model.Session;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SessionUtil 테스트")
class SessionUtilTest {

    private SessionUtil sessionUtil = new SessionUtil();

    @BeforeEach
    void setup() {
        sessionUtil.clearSession();
    }

    @Test
    @DisplayName("setSession 및 getSession 메서드 테스트")
    void testSetAndGetSession() {
        // given
        Session session = new Session();
        String userId = "chocochip";
        session.setAttribute("userId", userId);

        // when
        String sessionId = "random_session_id";
        sessionUtil.setSession(sessionId, session);
        Session retrievedSession = sessionUtil.getSession(sessionId);

        // then
        assertEquals(retrievedSession.getAttribute("userId"), userId);

    }

    @Test
    @DisplayName("getSessionByUser 메서드 - 존재하지 않는 사용자로 세션 조회")
    void testGetSessionByUserWithNonExistentUser() {
        // given
        Session session = new Session();

        // when & then
        assertThrows(UnauthorizedException.class, () -> sessionUtil.getSessionByUser(session));
    }

}
