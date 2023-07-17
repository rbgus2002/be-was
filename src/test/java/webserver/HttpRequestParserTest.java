package webserver;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpMethod;
import webserver.http.HttpRequest;
import webserver.http.HttpVersion;
import webserver.http.URL;

import java.util.List;
import java.util.Map;

class HttpRequestParserTest {
    private static HttpRequestParser parser;

    @BeforeAll
    static void init() {
        parser = new HttpRequestParser();
    }

    @DisplayName("Http get 요청 메세지 Parsing Test ")
    @Test
    void getTest() {
        String httpMessage = "GET /search?q=1,2 HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept: image/gif, image/jpeg, */*\r\n" +
                "Accept-Language: en-us\r\n" +
                "Accept-Encoding: gzip, deflate\r\n" +
                "User-Agent: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)\r\n\r\n";

        HttpRequest httpRequest = parser.parseHttpMessage(httpMessage);


        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.GET);

        URL url = httpRequest.getURL();
        softAssertions.assertThat(url.getPath()).isEqualTo("/");
        softAssertions.assertThat(url.getQueryParameter()).hasSize(0);

        softAssertions.assertThat(httpRequest.getHttpVersion()).isEqualTo(HttpVersion.V1_1);

        Map<String, List<String>> metaData = httpRequest.getMetaData();
        softAssertions.assertThat(metaData).containsEntry("Host", List.of("localhost:8080"));
        softAssertions.assertThat(metaData).containsEntry("Accept", List.of("image/gif", "image/jpeg", "*/*"));
        softAssertions.assertThat(metaData).containsEntry("Accept-Language", List.of("en-us"));
        softAssertions.assertThat(metaData).containsEntry("Accept-Encoding", List.of("gzip", "deflate", "*/*"));
        softAssertions.assertThat(metaData).containsEntry("Accept", List.of("image/gif", "image/jpeg", "*/*"));
    }

    @DisplayName("Http Post 요청 메세지 Parsing Test ")
    @Test
    void postTest() {
        String httpMessage = "POST / HTTP/1.0\r\n" +
                "Host: localhost:8080\r\n" +
                "Content-Type: application/json\r\n" +
                "Accept: */*\r\n" +
                "Accept-Encoding: gzip, deflate, */*\r\n" +
                "Content-Length: 44\r\n\r\n" +
                "{\r\n" +
                "    \"id\":\"1234\",\r\n" +
                "    \"password\" : \"1234\"\r\n" +
                "}";

        HttpRequest httpRequest = parser.parseHttpMessage(httpMessage);
        Map<String, List<String>> metaData = httpRequest.getMetaData();

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.POST);

        URL url = httpRequest.getURL();
        softAssertions.assertThat(url.getPath()).isEqualTo("/");
        softAssertions.assertThat(url.getQueryParameter()).hasSize(0);

        softAssertions.assertThat(httpRequest.getHttpVersion()).isEqualTo(HttpVersion.V1_0);

        softAssertions.assertThat(metaData).containsEntry("Host", List.of("localhost:8080"));
        softAssertions.assertThat(metaData).containsEntry("Content-Type", List.of("application/json"));
        softAssertions.assertThat(metaData).containsEntry("Content-Length", List.of("44"));
        softAssertions.assertThat(metaData).containsEntry("Accept-Encoding", List.of("gzip", "deflate", "*/*"));
        softAssertions.assertThat(metaData).containsEntry("Accept", List.of("*/*"));

        String body = httpRequest.getBody();
        softAssertions.assertThat(body).isEqualTo(
                "Content-Length: 44\r\n\r\n" +
                        "{\r\n" +
                        "    \"id\":\"1234\",\r\n" +
                        "    \"password\" : \"1234\"\r\n" +
                        "}");
    }
}
