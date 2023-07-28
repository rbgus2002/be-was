package webserver;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.request.HttpRequest;
import webserver.request.QueryParameter;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.StringUtils.appendNewLine;

@DisplayName("RequestHeader 테스트")
class HttpRequestTest {

    final String simpleRequestLine = "GET /index.html HTTP/1.1";
    final String SIMPLE_REQUEST_URL = "/index.html";
    final String createRequestLine = "GET /user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net HTTP/1.1";
    final String CREATE_REQUEST_URL = "/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
    final String CREATE_REQUEST_PATH = "/user/create";

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
    @DisplayName("Request Line에서 path 부분만 가져온다.")
    void getRequestPath() throws IOException {
        //given
        HttpRequest httpRequest = buildRequestHeader(createRequestLine);

        //when
        String requestPath = httpRequest.getRequestPath();

        //then
        assertEquals(CREATE_REQUEST_PATH, requestPath);
    }

    @Test
    @DisplayName("Request Line에서 Query 부분만 가져온다.")
    void getRequestQuery() throws IOException {
        //given
        HttpRequest httpRequest = buildRequestHeader(createRequestLine);

        //when
        QueryParameter query = httpRequest.getQuery();

        //then
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(query.getValue("userId")).isEqualTo("javajigi");
        softAssertions.assertThat(query.getValue("password")).isEqualTo("password");
        softAssertions.assertThat(query.getValue("name")).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1");
        softAssertions.assertThat(query.getValue("email")).isEqualTo("javajigi%40slipp.net");
        softAssertions.assertThat(query.size()).isEqualTo(4);
        softAssertions.assertAll();
    }

}
