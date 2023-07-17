package webserver.http;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

class HttpRequestTest {
    @Test
    @DisplayName("HTTP Request 메시지를 파싱할 수 있어야 한다")
    void create() {
        //given
        SoftAssertions softAssertions = new SoftAssertions();

        String requestBody = "This is the sample request body data.\r\n" +
                        "It contains multiple lines.\r\n" +
                        "This is line 3.\r\n" +
                        "And here's the final line.";

        String requestMessage = "GET /hello-world HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:90.0) Gecko/20100101 Firefox/90.0\r\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\r\n" +
                "Accept-Language: en-US,en;q=0.5\r\n" +
                "Accept-Encoding: gzip, deflate\r\n" +
                "Connection: keep-alive\r\n" +
                "Upgrade-Insecure-Requests: 1\r\n" +
                "Cache-Control: max-age=0\r\n" +
                "\r\n" +
                requestBody;

        InputStream inputStream = new ByteArrayInputStream(requestMessage.getBytes());

        try {
            //when
            HttpRequest httpRequest = new HttpRequest(inputStream);

            //then
            softAssertions.assertThat(httpRequest.get("Method")).isEqualTo("GET");
            softAssertions.assertThat(httpRequest.get("URI")).isEqualTo("/hello-world");
            softAssertions.assertThat(httpRequest.get("Version")).isEqualTo("HTTP/1.1");
            softAssertions.assertThat(httpRequest.get("Host")).isEqualTo("localhost:8080");
            softAssertions.assertThat(httpRequest.get("User-Agent")).isEqualTo("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:90.0) Gecko/20100101 Firefox/90.0");
            softAssertions.assertThat(httpRequest.get("Accept")).isEqualTo("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            softAssertions.assertThat(httpRequest.get("Accept-Language")).isEqualTo("en-US,en;q=0.5");
            softAssertions.assertThat(httpRequest.get("Accept-Encoding")).isEqualTo("gzip, deflate");
            softAssertions.assertThat(httpRequest.get("Connection")).isEqualTo("keep-alive");
            softAssertions.assertThat(httpRequest.get("Upgrade-Insecure-Requests")).isEqualTo("1");
            softAssertions.assertThat(httpRequest.get("Cache-Control")).isEqualTo("max-age=0");
            softAssertions.assertThat(httpRequest.getBody()).isEqualTo(requestBody);
            softAssertions.assertAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
