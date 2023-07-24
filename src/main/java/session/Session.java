package session;

import java.util.Map;
import java.util.UUID;

import com.google.common.collect.Maps;

import model.User;

public class Session {
	private Map<String, User> sessionStore = Maps.newConcurrentMap();

	public String createSession(User user) {
		String sessionId = UUID.randomUUID().toString();
		sessionStore.put(sessionId, user);
		return sessionId;
	}

	public User getUser(String sessionId) {
		if (sessionStore.containsKey(sessionId)) {
			return sessionStore.get(sessionId);
		}
		throw new IllegalArgumentException("등록되지 않은 세션 ID가 입력되었습니다.");
	}
}
