package webserver.http;

import controller.SignupController;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.request.HttpRequest;
import webserver.http.request.HttpMethod;

import java.util.Map;

class HttpRequestTest {

    private final HttpMethod METHOD = HttpMethod.GET;
    private final String URL = "/user/create";
    private final String VERSION = "HTTP/1.1";
    private final String USERID_VAL = "42";
    private final String PASSWORD_VAL = "sss";
    private final String NAME_VAL = "sss";
    private final String EMAIL_VAL = "sss@naver.com";
    HttpRequest httpRequest;

    SoftAssertions softAssertions = new SoftAssertions();

    @BeforeEach
    void setup() {
        httpRequest = new HttpRequest(METHOD + " " +  URL +
                "?" + SignupController.USERID_KEY + "=" + USERID_VAL + "&" +
                SignupController.PASSWORD_KEY + "=" + PASSWORD_VAL + "&" +
                SignupController.NAME_KEY + "=" + NAME_VAL + "&" +
                SignupController.EMAIL_KEY + "=" + EMAIL_VAL + " " + VERSION, null);

    }

    @Test
    @DisplayName("url이 파싱이 제대로 되는지?")
    void url_parsing() {
        //given

        //when

        //then
        softAssertions.assertThat(METHOD).isEqualTo(httpRequest.getMethod());
        softAssertions.assertThat(URL).isEqualTo(httpRequest.getUrl());
        softAssertions.assertThat(VERSION).isEqualTo(httpRequest.getVersion());

        softAssertions.assertAll();
    }


    @Test
    @DisplayName("쿼리 파싱이 제대로 되는지?")
    void query_parsing() {
        //given
        Map<String, String> queries = httpRequest.getFormDataMap();

        //when

        //then
        softAssertions.assertThat(USERID_VAL).isEqualTo(queries.get(SignupController.USERID_KEY));
        softAssertions.assertThat(PASSWORD_VAL).isEqualTo(queries.get(SignupController.PASSWORD_KEY));
        softAssertions.assertThat(NAME_VAL).isEqualTo(queries.get(SignupController.NAME_KEY));
        softAssertions.assertThat(EMAIL_VAL).isEqualTo(queries.get(SignupController.EMAIL_KEY));

        softAssertions.assertAll();
    }

    @Test
    @DisplayName("비정상 값이 들어온다면 어떻게 되는지?")
    void not_correct() {
        //given

        //when

        //then
        softAssertions.assertThatCode(() -> new HttpRequest("error",null)).isEqualTo(RuntimeException.class);
        softAssertions.assertThatCode(() -> new HttpRequest("GET error",null)).isEqualTo(RuntimeException.class);
    }


}