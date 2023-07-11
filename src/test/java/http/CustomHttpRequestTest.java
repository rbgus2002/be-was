package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class CustomHttpRequestTest {

    @Test
    @DisplayName("CustomHttpRequest가 생성된다.")
    void createCustomHttpRequest() throws URISyntaxException, IOException {
        //given
        List<String> strings = List.of("GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*");

        //when
        CustomHttpRequest customHttpRequest = new CustomHttpRequest(strings);

        //then
        URI expectedUri = new URI("http://localhost:8080/user/create?userId=javajigi&password=password&name=박재성&email=javajigi@slipp.net");
        assertThat(customHttpRequest.uri()).isEqualTo(expectedUri);
        assertThat(customHttpRequest.method()).isEqualTo(HttpMethod.GET.toString());
        assertThat(customHttpRequest.version()).isEqualTo(Optional.of(HttpClient.Version.HTTP_1_1));
    }
}