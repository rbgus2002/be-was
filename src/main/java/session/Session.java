package session;

import java.util.Map;
import java.util.UUID;

import com.google.common.collect.Maps;

import model.User;

public class Session {
	private static Session SESSION = new Session();
	private Map<String, User> sessionToUserStore = Maps.newConcurrentMap();
	private Map<User, String> userToSessionStore = Maps.newConcurrentMap();

	private Session() {
	}

	public static Session newInstance() {
		return SESSION;
	}

	public String createSession(User user) {
		String sessionId = UUID.randomUUID().toString();
		sessionToUserStore.put(sessionId, user);
		userToSessionStore.put(user, sessionId);
		return sessionId;
	}

	public User getUser(String sessionId) throws IllegalArgumentException {
		if (sessionToUserStore.containsKey(sessionId)) {
			return sessionToUserStore.get(sessionId);
		}
		throw new IllegalArgumentException("등록되지 않은 세션 ID가 입력되었습니다.");
	}

	public String getSessionId(User user) throws IllegalArgumentException {
		if (userToSessionStore.containsKey(user)) {
			return userToSessionStore.get(user);
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
