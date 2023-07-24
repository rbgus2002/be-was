package webserver.session;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        String sessionId = sessionManager.addSession("1");

        Assertions.assertThat(sessionManager.find(sessionId)).isEqualTo("1");
    }

    @DisplayName("세션 제거 테스트")
    @Test
    void removeSession() {
        String sessionId = sessionManager.addSession("1");

        sessionManager.remove(sessionId);

        assertFalse(sessionManager.contains(sessionId));
    }
}