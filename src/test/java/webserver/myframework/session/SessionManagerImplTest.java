package webserver.myframework.session;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;


@DisplayName("SessionManager 테스트")
class SessionManagerImplTest {
    SessionManager sessionManager;

    @BeforeEach
    void setUp() {
        sessionManager = new SessionManagerImpl();
    }

    @Nested
    @DisplayName("createSession method")
    class CreateSessionMethod {
        @Test
        @DisplayName("세션을 생성한다")
        void createSessionAndPutIntoManager() {
            //given
            String sessionId = String.valueOf(UUID.randomUUID());

            //when
            sessionManager.createSession(sessionId);

            //then
            Session session = sessionManager.findSession(sessionId);
            assertThat(session).isNotNull();
        }
    }

    @Nested
    @DisplayName("findSession method")
    class FindSession {
        @Test
        @DisplayName("맵으로부터 세션을 찾아 반환한다")
        void findSessionFromMapAndReturnIt() {
            //given
            String sessionId = String.valueOf(UUID.randomUUID());
            sessionManager.createSession(sessionId);

            //when
            Session session = sessionManager.findSession(sessionId);
            Session notExistSession = sessionManager.findSession("notExist");

            //then
            assertThat(session).isNotNull();
            assertThat(notExistSession).isNull();
        }
    }

    @Nested
    @DisplayName("deleteSession method")
    class DeleteSession {
        @Test
        @DisplayName("세션을 삭제한다")
        void deleteSession() {
            //given
            String sessionId = String.valueOf(UUID.randomUUID());
            sessionManager.createSession(sessionId);

            //when
            sessionManager.deleteSession(sessionId);

            //then
            Session deletedSession = sessionManager.findSession(sessionId);
            assertThat(deletedSession).isNull();
        }
    }
}
