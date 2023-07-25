package webserver.session;

import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.reponse.HttpResponse;
import webserver.request.HttpRequest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UserSessionManagerTest {
    UserSessionManager userSessionManager;


    @BeforeEach
    void init() {
        userSessionManager = UserSessionManager.getInstance();
    }

    @Test
    @DisplayName("세션을 등록하면 해당 세션 id로 요청했을 때 일치하는 유저를 반환해야 한다.")
    void putSession() {
        User user = new User("abc","abc","조형준","qewr@naver.com");
        HttpResponse response = new HttpResponse();

        String sessionId = userSessionManager.putSession(user, response);
        Map<String, String> cookieMap = new HashMap<>();
        cookieMap.put("sid",sessionId);
        HttpRequest request = new HttpRequest(null,null,null,null,null, cookieMap,null);

        User extractedUser = userSessionManager.getSession(request);
        assertEquals(user.getUserId(), extractedUser.getUserId());
        assertEquals(user.getName(), extractedUser.getName());
        assertEquals(user.getEmail(), extractedUser.getEmail());
        assertEquals(user.getPassword(), extractedUser.getPassword());

    }

    @Test
    @DisplayName("잘못된 세션 아이디를 보낼 경우 null을 반환해야 한다.")
    void getNullSession() {
        Map<String, String> cookieMap = new HashMap<>();
        cookieMap.put("sid", "abcd");
        HttpRequest request = new HttpRequest(null,null,null,null,null, cookieMap,null);

        User extractedUser = userSessionManager.getSession(request);

        assertTrue(extractedUser == null);
    }
}