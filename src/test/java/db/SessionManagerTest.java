package db;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SessionManagerTest {

    String sessionId;

    @BeforeEach
    void setUp() {
        sessionId = SessionManager.createSession();
    }

    @AfterEach
    void tearDown() {
        SessionManager.invalidateSession(sessionId);
    }

    @Test
    @DisplayName("세션을 생성한다.")
    void createSession() {
        Session session = SessionManager.getSession(sessionId);

        assertThat(session).isNotNull();
    }

    @Test
    @DisplayName("세션을 제거하고 세션을 조회하면 null을 반환한다.")
    void invalidateSession() {
        //when
        SessionManager.invalidateSession(sessionId);
        Session session = SessionManager.getSession(sessionId);

        //then
        assertThat(session).isNull();
    }
}