package webserver;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpResponse;
import webserver.http.HttpVersion;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class HttpResponseStringifierTest {
    private static final HttpResponseStringifier httpResponseStringifier = new HttpResponseStringifier();

    @DisplayName("Response 객체를 String 형태의 메세지로 변환하는 기능 테스트")
    @Test
    void stringifierTest() {
        HttpVersion httpVersion = HttpVersion.V1_1;
        HttpStatus httpStatus = HttpStatus.OK;
        Map<String, List<String>> metaData = getMetaDataFixture();
        byte[] body = "<html><body><h1>It works!</h1></body></html>".getBytes(StandardCharsets.UTF_8);
        HttpResponse httpResponse = new HttpResponse(httpVersion, httpStatus, metaData, body);

        String httpResponseMessage = httpResponseStringifier.stringify(httpResponse);

        Assertions.assertThat(httpResponseMessage).isEqualTo(
                "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: text/html;charset=utf-8\r\n" +
                        "Content-Length: 11\r\n" +
                        "\r\n" +
                        "<html><body><h1>It works!</h1></body></html>");
        )
    }

    private Map<String, List<String>> getMetaDataFixture() {
        Map<String, List<String>> metaData = new HashMap<>();
        metaData.put("Content-Type", List.of("text/html;charset=utf-8"));
        metaData.put("Content-Length", List.of("11"));
        return metaData;
    }
}
