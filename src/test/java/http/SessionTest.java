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
        assertThat(lastAccessTime).isLessThan(System.currentTimeMillis());
    }


}