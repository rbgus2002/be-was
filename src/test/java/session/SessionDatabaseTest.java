package session;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.User;
import webserver.session.SessionDatabase;

class SessionDatabaseTest {
	SessionDatabase sessionDatabase;

	@BeforeEach
	void setUp() {
		sessionDatabase = SessionDatabase.getInstance();
	}

	@Test
	@DisplayName("User 정보로 새로운 세션을 만들 수 있어야 한다")
	void createNewSession() {
		User user = User.of("testId", "testPw", "testName", "test@email.com");

		String sessionId = sessionDatabase.createSession(user.getUserId());

		assertThat(sessionDatabase.getUserId(sessionId)).isEqualTo(user.getUserId());
	}

	@Test
	@DisplayName("등록되지 않은 세션 ID로 조회를 시도하면 예외가 발생해야 한다")
	void invalidSessionId() {
		String sessionId = UUID.randomUUID().toString();

		assertThatThrownBy(() -> {
			sessionDatabase.getUserId(sessionId);
		}).isInstanceOf(IllegalArgumentException.class).hasMessage("등록되지 않은 세션 ID가 입력되었습니다.");
	}

	@Test
	@DisplayName("추가된 세션을 삭제할 수 있어야 한다")
	void removeSession() {
		User user = User.of("testId", "testPw", "testName", "test@email.com");
		String sessionId = sessionDatabase.createSession(user.getUserId());

		sessionDatabase.removeSession(sessionId);

		assertThatThrownBy(() -> {
			sessionDatabase.getUserId(sessionId);
		}).isInstanceOf(IllegalArgumentException.class).hasMessage("등록되지 않은 세션 ID가 입력되었습니다.");
	}
}
