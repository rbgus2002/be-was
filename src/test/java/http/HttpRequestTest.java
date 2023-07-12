package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {

    @Test
    @DisplayName("HttpRequest가 생성된다.")
    void createCustomHttpRequest() throws URISyntaxException, IOException {
        //given
        List<String> strings = List.of("GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*");

        //when
        HttpRequest httpRequest = new HttpRequest(strings);

        //then
        URI expectedUri = new URI("http://localhost:8080/user/create?userId=javajigi&password=password&name=박재성&email=javajigi@slipp.net");
        assertThat(httpRequest.uri()).isEqualTo(expectedUri);
        assertThat(httpRequest.method()).isEqualTo(HttpMethod.GET);
        assertThat(httpRequest.version()).isEqualTo(HttpClient.Version.HTTP_1_1);
    }
}