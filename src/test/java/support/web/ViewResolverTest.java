package support.web;

import db.Database;
import model.Session;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import support.web.view.ViewResolver;
import webserver.request.HttpRequest;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("뷰 리솔버 테스트")
class ViewResolverTest {

    HttpRequest request;
    HttpRequest requestWithNoCookie;

    final String USERID = "sampleUserId";
    final String PASSWORD = "samplePassword";
    final String NAME = "sampleName";
    final String EMAIL = "email.sample";

    @BeforeEach
    void setUp() {
        Database.clear();

        User user = new User(USERID, PASSWORD, NAME, EMAIL);
        Database.addUser(user);
        Session session = new Session();
        session.setUser(user);
        Database.addSession(session);

        HttpRequest.RequestHeaderBuilder requestHeaderBuilder = new HttpRequest.RequestHeaderBuilder();
        requestHeaderBuilder.requestLine("GET /index.html HTTP/1.1");
        requestHeaderBuilder.header("Cookie: sid=" + session.getSessionId());

        request = requestHeaderBuilder.build();

        HttpRequest.RequestHeaderBuilder requestWithNoCookieHeaderBuilder = new HttpRequest.RequestHeaderBuilder();
        requestWithNoCookieHeaderBuilder.requestLine("GET /index.html HTTP/1.1");
        requestWithNoCookie = requestWithNoCookieHeaderBuilder.build();
    }


    @Nested
    @DisplayName("특수 구분 해석 테스트")
    class Passer {

        @ParameterizedTest
        @CsvSource(value = {
                "<% SESSION.GET_USERNAME %>" + "," + NAME,
                "<li><a><% SESSION.GET_USERNAME %></a></li>" + "," + "<li><a>" + NAME + "</a></li>",
                "<% SESSION.IS_PRESENT <li><a><% SESSION.GET_USERNAME %></a></li> %>" + "," + "<li><a>" + NAME + "</a></li>"
        })
        @DisplayName("파싱 테스트")
        void syntaxParse(String syntax, String expected) {
            //given

            //when
            String result = ViewResolver.parseLoop(syntax, request);

            //then
            assertThat(result).isEqualTo(expected);

        }

        @ParameterizedTest
        @CsvSource(value = {
                "<% SESSION.GET_USERNAME %>" + "," + "",
                "<li><a><% SESSION.GET_USERNAME %></a></li>" + "," + "<li><a>" + "" + "</a></li>",
                "<% SESSION.IS_PRESENT <li><a><% SESSION.GET_USERNAME %></a></li> %>" + "," + ""
        })
        @DisplayName("파싱 테스트: 실패 분기")
        void syntaxParseBranch(String syntax, String expected) {
            //given
            if (expected == null) {
                expected = "";
            }

            //when
            String result = ViewResolver.parseLoop(syntax, requestWithNoCookie);

            //then
            assertThat(result).isEqualTo(expected);

        }

    }

}