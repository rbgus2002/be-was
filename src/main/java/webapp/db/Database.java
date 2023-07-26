package webapp.db;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

import webapp.model.User;

public class Database {
	private static Map<String, User> users = Maps.newConcurrentMap();

	public static void addUser(User user) {
		users.put(user.getUserId(), user);
	}

	public static User findUserById(String userId) {
		return users.get(userId);
	}

	public static Collection<User> findAll() {
		return users.values();
	}
}
