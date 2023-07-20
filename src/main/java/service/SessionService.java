package service;

import db.Database;
import model.Session;

import java.util.Collection;
import java.util.Map;

public class SessionService {

    public static Session getSessionByUserId(String userId) {
        // 해당 userId에 해당하는 Session 존재하는지 확인
        Session session = Database.findSessionByUserId(userId);
        // 존재하지 않으면 새로 Session 생성하여 제공
        if(session == null) {
            session = Database.addSession(userId);
        }

        return session;
    }
    public static String getUserIdBySid(String sid) {
        Collection<Session> sessionList = Database.findAllSession();
        return sessionList.stream()
                .filter(session -> session.getSessionId().equals(sid))
                .map(Session::getUserId)
                .findFirst()
                .orElse(null);
    }

    public static Collection<Session> getAllSession() {
        return Database.findAllSession();
    }

    public static Session updateSession(String userId) {
        Session session = new Session(userId);
        Database.updateSession(userId, session);
        return session;
    }

    public static void clearSessionDatabase() {
        Database.deleteAllSession();
    }
}
