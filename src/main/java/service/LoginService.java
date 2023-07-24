package service;

import db.Database;
import webserver.session.Session;

public class LoginService {
	public static boolean login(String userId, String password) throws IllegalArgumentException {
		return Database.verifyUser(userId, password);
	}

	public static String checkSession(String sessionId) throws IllegalArgumentException {
		Session session = Session.newInstance();
		String userId = session.getUserId(sessionId);
		return userId;
	}
}
