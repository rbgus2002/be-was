package db;

import com.google.common.collect.Maps;

import model.User;

import java.util.Collection;
import java.util.Map;

public class Database {
	public static final String USERID_ALREADY_EXISTS_MESSAGE = "이미 동일한 사용자 아이디가 존재합니다.";
	private static Map<String, User> users = Maps.newConcurrentMap();

	public static void dropAll() {
		users = Maps.newConcurrentMap();
	}

	public static boolean verifyUser(String userId, String password) {
		if (users.containsKey(userId)) {
			return users.get(userId).getPassword().equals(password);
		}
		throw new IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않아 인증에 실패했습니다.");
	}

	public static void addUser(User user) throws IllegalArgumentException {
		CheckDuplicateUserId(user.getUserId());
		users.put(user.getUserId(), user);
	}

	private static void CheckDuplicateUserId(final String userId) throws IllegalArgumentException {
		if (users.containsKey(userId)) {
			throw new IllegalArgumentException(USERID_ALREADY_EXISTS_MESSAGE);
		}
	}

	public static User findUserById(String userId) {
		if (users.containsKey(userId)) {
			return users.get(userId);
		}
		throw new IllegalArgumentException("존재하지 않는 ID를 조회할 수 없습니다.");
	}

	public static Collection<User> findAll() {
		return users.values();
	}
}
