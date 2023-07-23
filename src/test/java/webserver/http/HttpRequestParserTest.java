package webserver.http;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.message.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestParserTest {
    private static HttpRequestParser parser;

    @BeforeAll
    static void init() {
        parser = new HttpRequestParser();
    }

    @DisplayName("Http get 요청 메세지 Parsing Test ")
    @Test
    void getTest() throws IOException {
        String httpMessage = "GET /search?q=1,2 HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept: image/gif, image/jpeg, */*\r\n" +
                "Accept-Language: en-us\r\n" +
                "Accept-Encoding: gzip, deflate\r\n" +
                "User-Agent: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1)\r\n\r\n";
        InputStream inputStream = new ByteArrayInputStream(httpMessage.getBytes());

        HttpRequest httpRequest = parser.parseHttpRequest(inputStream);

        assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.GET);

        URL url = httpRequest.getURL();
        assertThat(url.getPath()).isEqualTo("/search");
        assertThat(url.getQueryParameter()).hasSize(1);

        assertThat(httpRequest.getHttpVersion()).isEqualTo(HttpVersion.V1_1);

        HttpHeaders headers = httpRequest.getHttpHeaders();
        assertThat(headers.getSingleValue("Host")).isEqualTo("localhost:8080");
        assertThat(headers.get("Accept")).contains("image/gif", "image/jpeg", "*/*");
        assertThat(headers.getSingleValue("Accept-Language")).isEqualTo("en-us");
        assertThat(headers.get("Accept-Encoding")).contains("gzip", "deflate");
    }

    @DisplayName("Http Post 요청 메세지 Parsing Test ")
    @Test
    void postTest() throws IOException {
        String httpMessage = "POST / HTTP/1.0\r\n" +
                "Host: localhost:8080\r\n" +
                "Content-Type: application/json\r\n" +
                "Accept: */*\r\n" +
                "Accept-Encoding: gzip, deflate, */*\r\n" +
                "Content-Length: 44\r\n\r\n" +
                "{\n" +
                "    \"id\":\"1234\",\n" +
                "    \"password\" : \"1234\"\n" +
                "}";
        InputStream inputStream = new ByteArrayInputStream(httpMessage.getBytes());

        HttpRequest httpRequest = parser.parseHttpRequest(inputStream);

        assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.POST);

        URL url = httpRequest.getURL();
        assertThat(url.getPath()).isEqualTo("/");
        assertThat(url.getQueryParameter()).hasSize(0);

        assertThat(httpRequest.getHttpVersion()).isEqualTo(HttpVersion.V1_0);

        HttpHeaders headers = httpRequest.getHttpHeaders();
        assertThat(headers.getSingleValue("Host")).isEqualTo("localhost:8080");
        assertThat(headers.getSingleValue("Content-Type")).isEqualTo("application/json");
        assertThat(headers.getSingleValue("Content-Length")).isEqualTo("44");
        assertThat(headers.get("Accept-Encoding")).contains("gzip", "deflate", "*/*");
        assertThat(headers.getSingleValue("Accept")).isEqualTo("*/*");

        char[] body = httpRequest.getBody();

        assertThat(body).isEqualTo(
                        ("{\n" +
                        "    \"id\":\"1234\",\n" +
                        "    \"password\" : \"1234\"\n" +
                        "}").toCharArray());
    }
}
