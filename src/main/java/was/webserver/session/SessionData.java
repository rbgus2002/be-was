package was.webserver.session;

import java.time.LocalDateTime;

public class SessionData {

	private final String userId;
	private final LocalDateTime expiredTime;

	public SessionData(final String userId, final LocalDateTime expiredTime) {
		this.userId = userId;
		this.expiredTime = expiredTime;
	}

	public String getUserId() {
		return userId;
	}

	public LocalDateTime getExpiredTime() {
		return expiredTime;
	}
}
