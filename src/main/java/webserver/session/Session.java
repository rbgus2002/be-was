package webserver.session;

import java.util.UUID;

public class Session {

	public static final Integer MAX_AGE = 7200;
	private final UUID sid;
	private final String userId;

	private Session(UUID sid, String userId) {
		this.sid = sid;
		this.userId = userId;
	}

	public static Session from(String userId) {
		return new Session(UUID.randomUUID(), userId);
	}

	public UUID getSid() {
		return sid;
	}

	public String getUserId() {
		return userId;
	}
}
