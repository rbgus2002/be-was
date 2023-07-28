package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SessionTest {
    @Test
    @DisplayName("Session을 생성할 때 lastAccessTime이 저장되는 지 확인한다")
    void setLastAccessTime(){
        // given
        Session session = Session.of("sid", new Object());
        
        // when
        final long lastAccessTime = session.getLastAccessTime();
        
        // then
        assertThat(lastAccessTime).isLessThanOrEqualTo(System.currentTimeMillis());
    }

    @Test
    @DisplayName("세션은 최근 접근 시간 기준으로 30분 이내에 접근하는 경우 유효하다")
    void validateExpiration(){
        // given
        // when
        Session session = Session.of("sid", new Object());

        // then
        assertTrue(session.validateExpiration());
    }
}