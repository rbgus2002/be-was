package webserver.session;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class HttpSession {

	public static final String SESSION_ID = "SID";
	private static final long EXPIRED_TIME = 1L;
	private ConcurrentMap<String, SessionData> attributes = new ConcurrentHashMap<>();
	private static HttpSession instance = null;

	public static HttpSession getInstance() {
		if (instance == null) {
			instance = new HttpSession();
		}
		return instance;
	}

	private HttpSession() {}

	public String createSession(String username) {
		final UUID sessionId = UUID.randomUUID();
		final SessionData sessionData = new SessionData(sessionId.toString(), createExpiredTime());

		attributes.put(username, sessionData);
		return sessionId.toString();
	}

	private LocalDateTime createExpiredTime() {
		return LocalDateTime.now().plusHours(EXPIRED_TIME);
	}
}
