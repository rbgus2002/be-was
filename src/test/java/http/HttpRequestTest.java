package http;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.HttpUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.util.List;

class HttpRequestTest {

    @Test
    @DisplayName("default mime의 HttpRequest가 생성된다.")
    void createDefaultMimeHttpRequest() throws URISyntaxException, IOException {
        //given
        List<String> strings = List.of("GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*");
        BufferedReader bufferedReader = stringListToBufferedReader(strings);

        //when
        HttpRequest httpRequest = new HttpRequest(bufferedReader);

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
    void createPngHttpRequest() throws URISyntaxException, IOException {
        //given
        List<String> strings = List.of("GET /user/create.png HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*");
        BufferedReader bufferedReader = stringListToBufferedReader(strings);

        //when
        HttpRequest httpRequest = new HttpRequest(bufferedReader);

        //then
        URI expectedUri = new URI("http://localhost:8080/user/create.png");
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(httpRequest.uri()).isEqualTo(expectedUri);
            softAssertions.assertThat(httpRequest.method()).isEqualTo(HttpUtils.Method.GET);
            softAssertions.assertThat(httpRequest.version()).isEqualTo(HttpClient.Version.HTTP_1_1);
            softAssertions.assertThat(httpRequest.mime()).isEqualTo(Mime.PNG);
        });
    }

    private BufferedReader stringListToBufferedReader(List<String> strings) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : strings) {
            stringBuilder.append(str).append(System.lineSeparator());
        }
        String stringData = stringBuilder.toString();
        return new BufferedReader(new StringReader(stringData));
    }
}