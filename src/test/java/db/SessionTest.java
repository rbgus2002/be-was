package db;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SessionTest {

    String sessionId;

    @BeforeEach
    void setUp() {
        sessionId = Session.createSession();
    }

    @AfterEach
    void tearDown() {
        Session.invalidateSession(sessionId);
    }

    @Test
    @DisplayName("세션에 값을 저장한다.")
    void setAttribute() {
        //given
        String key = "hello";
        String value = "world";

        //when
        Session.setAttribute(sessionId, key, value);

        //then
        assertThat(Session.getAttribute(sessionId, key)).isEqualTo(value);
    }

    @Test
    @DisplayName("세션에 키가 없으면 null이 반환된다.")
    void getAttributeNull() {
        //when
        Object value = Session.getAttribute(sessionId, "b");

        //then
        assertThat(value).isNull();
    }


    @Test
    @DisplayName("세션의 값을 삭제한다.")
    void removeAttribute() {
        //given
        String key = "hi";
        String value = "hello";
        Session.setAttribute(sessionId, key, value);

        //when
        Session.removeAttribute(sessionId, key);

        //then
        Object result = Session.getAttribute(sessionId, key);
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("세션을 삭제하면 null을 얻는다.")
    void invalidateSessionGetNull() {
        //given
        String key = "hello";
        String value = "world";
        Session.setAttribute(sessionId, key, value);

        //when
        Session.invalidateSession(sessionId);

        //then
        assertThat(Session.getAttribute(sessionId, key)).isNull();
    }
}