package webserver.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;


@DisplayName("HttpRequestParserImpl 테스트")
class HttpRequestParserImplTest {
    final HttpRequestParser httpRequestParser = new HttpRequestParserImpl();
    static final String httpMessage =
            "GET /index.html HTTP/1.1\r\n" +
            "Host: localhost:8080\r\n" +
            "Connection: keep-alive\r\n" +
            "sec-ch-ua: \"Not.A/Brand\";v=\"8\", \"Chromium\";v=\"114\", \"Google Chrome\";v=\"114\"\r\n" +
            "sec-ch-ua-mobile: ?0\r\n" +
            "sec-ch-ua-platform: \"macOS\"\r\n" +
            "Upgrade-Insecure-Requests: 1\r\n" +
            "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36\r\n" +
            "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7\r\n" +
            "Sec-Fetch-Site: none\r\n" +
            "Sec-Fetch-Mode: navigate\r\n" +
            "Sec-Fetch-User: ?1\r\n" +
            "Sec-Fetch-Dest: document\r\n" +
            "Accept-Encoding: gzip, deflate, br\r\n" +
            "Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7\r\n" +
            "Cookie: Idea-13020a74=01685f19-8588-4907-88e8-60ee76288f21\r\n"
            +"\r\n" +
            "BodyBodyBody\r\n";

    @Nested
    @DisplayName("parse method")
    class parse {
        @Test
        @DisplayName("HTTP 요청 메세지의 정보를 담고있는 HttpRequest 객체를 반환한다")
        void returnHttpRequest() throws IOException {
            //given
            HashMap<String, String> expectHeaders = getExpectHeaders();
            InputStream inputStream = new ByteArrayInputStream(httpMessage.getBytes());

            //when
            HttpRequest httpRequest = httpRequestParser.parse(inputStream);

            //then
            verifyStartLine(httpRequest, HttpMethod.GET, "/index.html", "1.1");
            verifyHeaders(httpRequest, expectHeaders);
        }
    }

    @SuppressWarnings("SameParameterValue")
    private static void verifyStartLine(HttpRequest httpRequest,
                                        HttpMethod method,
                                        String uri,
                                        String version) {
        assertThat(httpRequest.getMethod()).isEqualTo(method);
        assertThat(httpRequest.getUri()).isEqualTo(uri);
        assertThat(httpRequest.getVersion()).isEqualTo(version);
    }

    private static void verifyHeaders(HttpRequest httpRequest,
                                        Map<String, String> headerInfos) {
        for (String headerName : headerInfos.keySet()) {
            verifyHeader(httpRequest, headerInfos, headerName);
        }
    }

    private static void verifyHeader(HttpRequest httpRequest, Map<String, String> headerInfos, String headerName) {
        String expectValue = headerInfos.get(headerName);
        String resultValue = httpRequest.getHeader(headerName);
        assertThat(resultValue).isEqualTo(expectValue);
    }

    private static HashMap<String, String> getExpectHeaders() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Host", "localhost:8080");
        headers.put("Connection", "keep-alive");
        headers.put("sec-ch-ua", "\"Not.A/Brand\";v=\"8\", \"Chromium\";v=\"114\", \"Google Chrome\";v=\"114\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "\"macOS\"");
        headers.put("Upgrade-Insecure-Requests", "1");
        headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36");
        headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7");
        headers.put("Sec-Fetch-Site", "none");
        headers.put("Sec-Fetch-Mode", "navigate");
        headers.put("Sec-Fetch-User", "?1");
        headers.put("Sec-Fetch-Dest", "document");
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Accept-Language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7");
        headers.put("Cookie", "Idea-13020a74=01685f19-8588-4907-88e8-60ee76288f21");
        return headers;
    }
}
