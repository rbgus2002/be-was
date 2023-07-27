package db;

import http.Session;
import model.User;
import model.factory.UserFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SessionManagerTest {
    @Test
    @DisplayName("세션이 정상적으로 만들어지는지 확인한다")
    void createSession(){
        // given, when
        final String sid = SessionManager.createSession("key", "value");

        // then
        assertNotNull(sid);
    }
    
    @Nested
    class fetchSession{
        @Test
        @DisplayName("세션이 존재하면 가져온다")
        void success(){
            // given
            final String sid = SessionManager.createSession("key", "value");

            // when
            final Session session = SessionManager.fetchSession(sid);

            // then
            assertNotNull(session);
        }
        
        @Test
        @DisplayName("세션이 존재하지 않으면 null 값을 가져온다")
        void fetchNullIfNotExist(){
            // given, when
            final Session session = SessionManager.fetchSession("invalid");

            // then
            assertNull(session);
        }
        
        @Test
        @DisplayName("세션을 가져오면 접근 시간을 현재 시간으로 수정한다")
        void SessionUpdateLastAccessTime(){
            // given
            final String sid = SessionManager.createSession("key", "value");
            final Session session = SessionManager.fetchSession(sid);
            final Long lastAccessTime = session.getLastAccessTime();

            // when
            final Session fetchedSession = SessionManager.fetchSession(sid);
            final Long fetchedLastAccessTime = fetchedSession.getLastAccessTime();
            
            // then
            assertThat(lastAccessTime).isLessThanOrEqualTo(fetchedLastAccessTime);
        }
    }
    
    @Test
    @DisplayName("세션에 sid에 해당하는 User가 존재하면 fetch 해온다")
    void fetchUser(){
        // given
        User user = UserFactory.createUserFrom(getData());
        String sid = SessionManager.createUserSession(user);

        // when
        User fetchedUser = SessionManager.fetchUser(sid);
        
        // then
        assertEquals(user, fetchedUser);
    }

    private Map<String, String> getData(){
        Map<String, String> data = new HashMap<>();
        data.put("userId", "rbgus2002");
        data.put("password", "0000");
        data.put("name", "최규현");
        data.put("email", "rbgus2002@naver.com");
        return data;
    }
}