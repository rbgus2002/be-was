package global.util;

import exception.UnauthorizedException;
import global.constant.Headers;
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
        User user = new User("testUserId", "testPassword", "testName", "testEmail");

        // when
        String sessionId = "random_session_id";
        sessionUtil.setSession(Headers.SESSION_ID.getKey() + "=" + sessionId, user);
        User retrievedUser = sessionUtil.getSession(Headers.SESSION_ID.getKey() + "=" + sessionId);

        // then
        assertEquals(user, retrievedUser);
    }

    @Test
    @DisplayName("getSessionByUser 메서드 - 존재하는 사용자에 대한 세션 조회")
    void testGetSessionByUserWithExistingUser() {
        // given
        User user = new User("testUserId", "testPassword", "testName", "testEmail");
        String sessionId = "random_session_id";
        sessionUtil.setSession(Headers.SESSION_ID.getKey() + "=" + sessionId, user);

        // when
        String retrievedSessionId = sessionUtil.getSessionByUser(user);

        // then
        assertEquals(sessionId, retrievedSessionId);
    }

    @Test
    @DisplayName("getSessionByUser 메서드 - 존재하지 않는 사용자로 세션 조회")
    void testGetSessionByUserWithNonExistentUser() {
        // given
        User user = new User("testUserId", "testPassword", "testName", "testEmail");

        // when & then
        assertThrows(UnauthorizedException.class, () -> sessionUtil.getSessionByUser(user));
    }

    @Test
    @DisplayName("getAllSessions 메서드 테스트")
    void testGetAllSessions() {
        // given
        User user1 = new User("testUserId1", "testPassword1", "testName1", "testEmail1");
        User user2 = new User("testUserId2", "testPassword2", "testName2", "testEmail2");
        String sessionId1 = "session_id_1";
        String sessionId2 = "session_id_2";

        sessionUtil.setSession(Headers.SESSION_ID.getKey() + "=" + sessionId1, user1);
        sessionUtil.setSession(Headers.SESSION_ID.getKey() + "=" + sessionId2, user2);

        // when
        var allSessions = sessionUtil.getAllSessions();

        // then
        assertEquals(2, allSessions.size());
        assertEquals(user1, allSessions.get(sessionId1));
        assertEquals(user2, allSessions.get(sessionId2));
    }

}
