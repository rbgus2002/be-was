package webserver.session;

import java.util.Map;
import java.util.UUID;

import com.google.common.collect.Maps;

public class SessionStorage {

	private static final Map<UUID, Session> storage = Maps.newConcurrentMap();

	public static Session createSession(String userId) {
		UUID uuid = UUID.randomUUID();
		Session session = Session.from(userId);
		storage.put(uuid, session);
		return session;
	}

}
