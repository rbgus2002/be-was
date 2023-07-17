package webserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.HttpRequest;
import webserver.request.Query;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static utils.StringUtils.appendNewLine;

@DisplayName("RequestHeader 테스트")
class HttpRequestTest {
    String simpleRequestLine = "GET /index.html HTTP/1.1";
    String SIMPLE_REQUEST_URL = "/index.html";
    String createRequestLine = "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1";
    String CREATE_REQUEST_URL = "/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
    String CREATE_REQUEST_PATH = "/user/create";

    String headers = appendNewLine("Host: localhost:8080", "Connection: keep-alive", "Accept: */*");
    String body = ""; // 본문 내용을 추가할 수 있습니다.

    @BeforeEach
    void setUp() {
    }

    HttpRequest buildRequestHeader(String request) throws IOException {
        String httpRequest = appendNewLine(request, headers, "", body);
        InputStream in = new ByteArrayInputStream(httpRequest.getBytes(StandardCharsets.UTF_8));
        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
        HttpRequest.RequestHeaderBuilder requestHeaderBuilder = new HttpRequest.RequestHeaderBuilder();
        String line = br.readLine();
        requestHeaderBuilder = requestHeaderBuilder.requestLine(line);
        line = br.readLine();
        while (!"".equals(line)) {
            requestHeaderBuilder.header(line);
            line = br.readLine();
        }
        return requestHeaderBuilder.build();
    }

    @Test
    @DisplayName("Request Line에서 정상적으로 요청 Url을 분리하는지 검증한다.")
    void parseRequestUrl() throws IOException {
        //given
        HttpRequest httpRequest = buildRequestHeader(simpleRequestLine);

        //when
        String requestUrl = httpRequest.getRequestUrl();
        String requestHeaders = httpRequest.getHeaders();

        //then
        assertEquals(SIMPLE_REQUEST_URL, requestUrl);
        assertNotEquals(0, requestHeaders.length());
    }

    @Test
    @DisplayName("Request Line에서 path 부분만 가져온다.")
    void getRequestPath() throws IOException {
        //given
        HttpRequest httpRequest = buildRequestHeader(createRequestLine);

        //when
        String requestUrl = httpRequest.getRequestUrl();
        String requestPath = httpRequest.getRequestPath();

        //then
        assertEquals(CREATE_REQUEST_URL, requestUrl);
        assertEquals(CREATE_REQUEST_PATH, requestPath);
    }

    @Test
    @DisplayName("Request Line에서 Query 부분만 가져온다.")
    void getRequestQuery() throws IOException {
        //given
        HttpRequest httpRequest = buildRequestHeader(createRequestLine);

        //when
        String requestUrl = httpRequest.getRequestUrl();
        Query query = httpRequest.getRequestQuery();

        //then
        assertEquals(CREATE_REQUEST_URL, requestUrl);
        assertEquals("javajigi", query.getValue("userId"));
        assertEquals("password", query.getValue("password"));
        assertEquals("%EB%B0%95%EC%9E%AC%EC%84%B1", query.getValue("name"));
        assertEquals("javajigi%40slipp.net", query.getValue("email"));
        assertEquals(4, query.size());
    }
}
