package webserver.session;

import java.util.Map;
import java.util.UUID;

import com.google.common.collect.Maps;

public class SessionDatabase {
	private static SessionDatabase SESSION_DATABASE = new SessionDatabase();
	private Map<String, String> sessionToUserStore = Maps.newConcurrentMap();
	private Map<String, String> userToSessionStore = Maps.newConcurrentMap();

	private SessionDatabase() {
	}

	public static SessionDatabase getInstance() {
		return SESSION_DATABASE;
	}

	public String createSession(String userId) {
		String sessionId = UUID.randomUUID().toString();
		sessionToUserStore.put(sessionId, userId);
		userToSessionStore.put(userId, sessionId);
		return sessionId;
	}

	public boolean containsSession(String sessionId) {
		return sessionToUserStore.containsKey(sessionId);
	}

	public String getUserId(String sessionId) throws IllegalArgumentException {
		if (sessionToUserStore.containsKey(sessionId)) {
			return sessionToUserStore.get(sessionId);
		}
		throw new IllegalArgumentException("등록되지 않은 세션 ID가 입력되었습니다.");
	}

	public String getSessionId(String userId) throws IllegalArgumentException {
		if (userToSessionStore.containsKey(userId)) {
			return userToSessionStore.get(userId);
		}
		throw new IllegalArgumentException("세션이 생성되지 않은 사용자입니다.");
	}

	public void removeSession(String sessionId) throws IllegalArgumentException {
		if (sessionToUserStore.containsKey(sessionId)) {
			userToSessionStore.remove(sessionToUserStore.get(sessionId));
			sessionToUserStore.remove(sessionId);
			return;
		}
		throw new IllegalArgumentException("세션 ID가 등록되지 않아 세션을 제거할 수 없습니다.");
	}
}
