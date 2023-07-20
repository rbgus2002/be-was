package webserver.session;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class HttpSession {

	private static final long EXPIRED_TIME = 1L;
	private ConcurrentMap<String, SessionData> attributes = new ConcurrentHashMap<>();

	public void createSession(String username) {
		final UUID sessionId = UUID.randomUUID();
		final SessionData sessionData = new SessionData(sessionId.toString(), createExpiredTime());

		attributes.put(username, sessionData);
	}

	private LocalDateTime createExpiredTime() {
		return LocalDateTime.now().plusHours(EXPIRED_TIME);
	}
}
