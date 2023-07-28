package service;

import db.UserDatabase;
import webserver.session.SessionDatabase;

public class LoginService {

	public static boolean login(String userId, String password) {
		try {
			UserDatabase.verifyUser(userId, password);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	public static boolean checkSession(String sessionId) throws IllegalArgumentException {
		SessionDatabase sessionDatabase = SessionDatabase.getInstance();
		return sessionDatabase.containsSession(sessionId);
	}

	public static String getUserIdFrom(String sessionId) throws IllegalArgumentException {
		SessionDatabase sessionDatabase = SessionDatabase.getInstance();
		return sessionDatabase.getUserId(sessionId);
	}
}
