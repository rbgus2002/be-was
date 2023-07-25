package webserver.session;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpSessionTest {

	@Test
	@DisplayName("sessionId를 생성하면 제대로 값이 저장되어야 한다")
	void saveSessionId() {
		// given
		final HttpSession httpSession = HttpSession.getInstance();

		// when
		final String sessionId = httpSession.createSession("haechan");

		// then
		Assertions.assertThat(httpSession.verifySession(sessionId)).isTrue();
	}

}