package webserver.session;

import java.time.LocalDateTime;

public class SessionData {

	private final String sessionId;
	private final LocalDateTime expiredTime;

	public SessionData(final String sessionId, final LocalDateTime expiredTime) {
		this.sessionId = sessionId;
		this.expiredTime = expiredTime;
	}

	public String getSessionId() {
		return sessionId;
	}

	public LocalDateTime getExpiredTime() {
		return expiredTime;
	}
}
