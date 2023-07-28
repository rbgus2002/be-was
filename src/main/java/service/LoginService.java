package service;

import db.Database;
import webserver.session.Session;

public class LoginService {

	public static boolean login(String userId, String password) {
		try {
			Database.verifyUser(userId, password);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	public static boolean checkSession(String sessionId) throws IllegalArgumentException {
		Session session = Session.getInstance();
		return session.containsSession(sessionId);
	}

	public static String getUserIdFrom(String sessionId) throws IllegalArgumentException {
		Session session = Session.getInstance();
		return session.getUserId(sessionId);
	}
}
