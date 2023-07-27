package webserver.session;

import java.util.UUID;

public class Session {

	public static final String SID = "sid";
	public static final Integer MAX_AGE = 7200;
	private final UUID sessionId;
	private final String userId;

	private Session(UUID sessionId, String userId) {
		this.sessionId = sessionId;
		this.userId = userId;
	}

	public static Session from(String userId) {
		return new Session(UUID.randomUUID(), userId);
	}

	public String getSessionId() {
		return sessionId.toString();
	}

	public String getUserId() {
		return userId;
	}
}
