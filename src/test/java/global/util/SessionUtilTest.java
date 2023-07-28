package global.util;

import exception.BadRequestException;
import global.constant.Headers;
import model.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("세션 테스트")
public class SessionUtilTest {

    private SessionUtil sessionUtil;

    @BeforeEach
    public void setUp() {
        sessionUtil = new SessionUtil();
        sessionUtil.clearSession();
    }

    @Test
    @DisplayName("세션을 생성한다.")
    public void testCreateSession() {
        String sessionId = sessionUtil.createSession();
        assertNotNull(sessionId);
        assertTrue(sessionUtil.getAllSessions().containsKey(sessionId));
    }

    @Test
    @DisplayName("세션을 조회한다.")
    public void testGetSession() {
        String sessionId = sessionUtil.createSession();
        Session session = sessionUtil.getSession("Cookie: " + Headers.SESSION_ID.getKey() + "=" + sessionId + ";");
        assertNotNull(session);
    }

    @Test
    @DisplayName("적절하지 않는 세션은 BasRequest 에러가 발생한다.")
    public void testInvalidHeader() {
        assertThrows(BadRequestException.class, () -> sessionUtil.getSession("Invalid Header"));
    }

    @Test
    @DisplayName("세션을 여러 개 생성한다.")
    public void testGetAllSessions() {
        String sid1 = sessionUtil.createSession();
        String sid2 = sessionUtil.createSession();
        String sid3 = sessionUtil.createSession();

        assertEquals(3, sessionUtil.getAllSessions().size());
        assertTrue(sessionUtil.getAllSessions().containsKey(sid1));
        assertTrue(sessionUtil.getAllSessions().containsKey(sid2));
        assertTrue(sessionUtil.getAllSessions().containsKey(sid3));
    }

    @Test
    @DisplayName("세션의 세부 설정에 성공한다.")
    public void testSetSession() {
        String sessionId = sessionUtil.createSession();
        Session session = new Session();
        session.setAttribute("userId", "user123");

        sessionUtil.setSession(sessionId, session);

        Session retrievedSession = sessionUtil.getSession("Cookie: " + Headers.SESSION_ID.getKey() + "=" + sessionId + ";");
        assertNotNull(retrievedSession);
        assertEquals("user123", retrievedSession.getAttribute("userId"));
    }
}
