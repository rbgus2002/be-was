package session;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import http.Cookie;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

class UserSessionManagerTest {

    @Test
    @DisplayName("세션 저장 시 세션을 통해 저장한 유저 객체에 접근할 수 있다.")
    void addUser() {
        //given
        String userId = "bigsand123";
        String password = "123";
        String name = "bigsand";
        String email = "bigsand@naver.com";
        User user = new User(userId, password, name, email);

        //when
        Cookie cookie = UserSessionManager.addUser(user);
        User expectUser = UserSessionManager.getUser(cookie.getValue());

        //then
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(expectUser.getUserId()).isEqualTo(userId);
            softAssertions.assertThat(expectUser.getPassword()).isEqualTo(password);
            softAssertions.assertThat(expectUser.getName()).isEqualTo(name);
            softAssertions.assertThat(expectUser.getEmail()).isEqualTo(email);
                }
        );
    }

}