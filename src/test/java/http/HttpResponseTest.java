package http;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HttpResponseTest {

    @Test
    @DisplayName("OK(200) 객체를 생성한다.")
    void createOk() {
        //given
        String path = "http://lucas.com/hello.html";
        Mime mime = Mime.HTML;

        //when
        HttpResponse httpResponse = HttpResponse.ok(path, mime);

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(httpResponse.getPath()).isEqualTo(path);
            softAssertions.assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.OK);
            softAssertions.assertThat(httpResponse.getContentType()).isEqualTo(Mime.HTML);
        });
    }

    @Test
    @DisplayName("REDIRECT(302) 객체를 생성한다.")
    void createRedirect() {
        //given
        String path = "http://lucas.com/hello";

        //when
        HttpResponse httpResponse = HttpResponse.redirect(path);

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(httpResponse.getPath()).isEqualTo(path);
            softAssertions.assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.FOUND);
        });
    }

    @Test
    @DisplayName("NOT_FOUND(404) 객체를 생성한다.")
    void createNotFound() {
        //given
        //when
        HttpResponse httpResponse = HttpResponse.notFound("path", Mime.CSS);

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(httpResponse.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
            softAssertions.assertThat(httpResponse.getPath()).isEqualTo("path");
            softAssertions.assertThat(httpResponse.getContentType()).isEqualTo(Mime.CSS);
        });
    }

    @Test
    @DisplayName("쿠키를 설정하고 조회한다.")
    void checkCookie() {
        //given
        HttpResponse httpResponse = HttpResponse.ok("path", Mime.ICO);

        //when
        httpResponse.setCookie("name", "han");
        httpResponse.setCookie("sid", "1234");

        //then
        Cookie name = new Cookie("name", "han");
        Cookie sid = new Cookie("sid", "1234");
        List<Cookie> expectedCookies = List.of(sid, name);

        List<Cookie> cookies = httpResponse.getCookies();
        assertThat(cookies).usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expectedCookies);
    }
}