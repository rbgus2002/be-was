package webserver.session;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

class SessionManagerTest {
    SessionManager sessionManager;

    @BeforeEach
    void init() {
        sessionManager = new SessionManager();
    }

    @DisplayName("세션 추가 및 조회 테스트")
    @Test
    void addSession() {
        Session session = sessionManager.addNewSession();

        assertThat(sessionManager.getSession(session.getId())).isEqualTo(session);
    }

    @DisplayName("세션 제거 테스트")
    @Test
    void removeSession() {
        Session session = sessionManager.addNewSession();

        sessionManager.remove(session.getId());

        assertFalse(sessionManager.contains(session.getId()));
    }
}