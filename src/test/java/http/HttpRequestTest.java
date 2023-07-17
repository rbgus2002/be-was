package http;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.HttpUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {

    @Test
    @DisplayName("default mime의 HttpRequest가 생성된다.")
    void createDefaultMimeHttpRequest() throws URISyntaxException, IOException {
        //given
        List<String> strings = List.of("GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*");

        //when
        HttpRequest httpRequest = new HttpRequest(strings);

        //then
        URI expectedUri = new URI("http://localhost:8080/user/create?userId=javajigi&password=password&name=박재성&email=javajigi@slipp.net");
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(httpRequest.uri()).isEqualTo(expectedUri);
            softAssertions.assertThat(httpRequest.method()).isEqualTo(HttpUtils.Method.GET);
            softAssertions.assertThat(httpRequest.version()).isEqualTo(HttpClient.Version.HTTP_1_1);
            softAssertions.assertThat(httpRequest.mime()).isEqualTo(Mime.DEFAULT);
        });
    }

    @Test
    @DisplayName("PNG HttpRequest 객체를 생성한다.")
    void createPngHttpRequest() throws URISyntaxException {
        //given
        List<String> strings = List.of("GET /user/create.png HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*");

        //when
        HttpRequest httpRequest = new HttpRequest(strings);

        //then
        URI expectedUri = new URI("http://localhost:8080/user/create.png");
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(httpRequest.uri()).isEqualTo(expectedUri);
            softAssertions.assertThat(httpRequest.method()).isEqualTo(HttpUtils.Method.GET);
            softAssertions.assertThat(httpRequest.version()).isEqualTo(HttpClient.Version.HTTP_1_1);
            softAssertions.assertThat(httpRequest.mime()).isEqualTo(Mime.PNG);
        });

    }

    @Test
    @DisplayName("URI 쿼리스트링이 맵으로 변환된다.")
    void convertQueryToMap() throws URISyntaxException, IOException {
        //given
        List<String> strings = List.of("GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*");
        HttpRequest httpRequest = new HttpRequest(strings);

        //when
        Map<String, String> parameters = httpRequest.parameters();

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(parameters.get("userId")).isEqualTo("javajigi");
            softAssertions.assertThat(parameters.get("password")).isEqualTo("password");
            softAssertions.assertThat(parameters.get("name")).isEqualTo("박재성");
            softAssertions.assertThat(parameters.get("email")).isEqualTo("javajigi@slipp.net");
        });

    }

    @Test
    @DisplayName("쿼리스트링이 없으면 빈 맵을 반환한다.")
    void returnEmptyMapIfQueryNotExist() throws URISyntaxException, IOException {
        //given
        List<String> strings = List.of("GET /index.html HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*");
        HttpRequest httpRequest = new HttpRequest(strings);

        //when
        Map<String, String> parameters = httpRequest.parameters();

        //then
        assertThat(parameters).isEmpty();
    }
}