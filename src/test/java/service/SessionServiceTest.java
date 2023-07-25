package service;

import db.Database;
import model.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.SoftAssertions;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class SessionServiceTest {
    @BeforeEach
    public void setup() {
        Database.deleteAllUser();
        Database.deleteAllSession();
    }

    @Test
    @DisplayName("세션 생성 후 같은 userId로 다시 가져오면 같은 세션을 반환해야 한다.")
    public void getSessionByUserId() {
        // Given
        Session addedSession = SessionService.addSession("jst0951");

        // When
        Session loadedSession = SessionService.getSessionByUserId("jst0951");

        // Then
        assertThat(addedSession).isEqualTo(loadedSession);
    }

    @Test
    @DisplayName("sid로 연결된 userId를 불러올 수 있어야 한다.")
    public void getSessionBySid() {
        // Given
        Session session = SessionService.addSession("jst0951");

        // When
        String userId = SessionService.getUserIdBySid(session.getSessionId());

        // Then
        assertThat(userId).isEqualTo("jst0951");
    }

    @Test
    @DisplayName("session이 유효한지 검사되어야 한다.")
    public void isSessionValid() {
        // Given
        Session session = SessionService.addSession("jst0951");
        String sid = session.getSessionId();

        // When
        boolean isValidSessionActual = SessionService.isSessionValid(sid);
        boolean isValidSessionFake = SessionService.isSessionValid("fake-session-id");

        // Then
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(isValidSessionActual).isTrue();
        assertions.assertThat(isValidSessionFake).isFalse();
    }

    @Test
    @DisplayName("getAllSession()은 모든 세션을 반환하여야 한다.")
    public void getAllSession() {
        // Given
        Session sessionOne = SessionService.addSession("jst0951");
        Session sessionTwo = SessionService.addSession("jst0082");

        // When
        Collection<Session> sessions = SessionService.getAllSession();

        // Then
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(sessions.size()).isEqualTo(2);
        assertions.assertThat(sessions.contains(sessionOne)).isTrue();
        assertions.assertThat(sessions.contains(sessionTwo)).isTrue();
    }

    @Test
    @DisplayName("세션 갱신 후 갱신된 세션(DB에 저장된 최신 세션)을 반환해야 한다.")
    public void updateSession() {
        // Given
        Session session = SessionService.addSession("jst0951");

        // When
        Session updatedSession = SessionService.updateSession("jst0951");
        Session savedSession = SessionService.getSessionByUserId("jst0951");

        // Then
        assertThat(savedSession).isEqualTo(updatedSession);
    }
}
