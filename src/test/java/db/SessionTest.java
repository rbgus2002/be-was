package db;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SessionTest {

    Session session;

    @BeforeEach
    void setUp() {
        session = new Session();
    }

    @Test
    @DisplayName("세션에 값을 저장한다.")
    void setAttribute() {
        //given
        String key = "hello";
        String value = "world";
        Session session = new Session();

        //when
        session.setAttribute(key, value);

        //then
        assertThat(session.getAttribute(key)).isEqualTo(value);
    }

    @Test
    @DisplayName("세션에 키가 없으면 null이 반환된다.")
    void getAttributeNull() {
        //when
        Object value = session.getAttribute("b");

        //then
        assertThat(value).isNull();
    }

    @Test
    @DisplayName("세션의 값을 삭제한다.")
    void removeAttribute() {
        //given
        String key = "hi";
        String value = "hello";
        session.setAttribute(key, value);

        //when
        session.removeAttribute(key);

        //then
        Object result = session.getAttribute(key);
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("세션의 접근 시간을 현재로 바꾼다.")
    void changeAccessTime() throws InterruptedException {
        long oldAccessedTime = session.getLastAccessedTime();

        Thread.sleep(1);
        session.setLastAccessedTimeNow();
        long nowAccessedTime = session.getLastAccessedTime();

        Thread.sleep(1);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(oldAccessedTime < nowAccessedTime).isTrue();
            softAssertions.assertThat(nowAccessedTime < System.currentTimeMillis()).isTrue();
        });
    }
}