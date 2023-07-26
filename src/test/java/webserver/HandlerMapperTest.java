package webserver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.request.HttpRequest;
import webserver.http.request.HttpRequestBody;
import webserver.http.request.HttpRequestHeaders;
import webserver.http.request.HttpRequestStartLine;

import java.io.BufferedReader;
import java.lang.reflect.Method;

@DisplayName("Handler mapper test")
class HandlerMapperTest {

    @Test
    @DisplayName("path에 맞는 handler를 가져오는지 확인한다.")
    void getHandler() {
        String startLine = "POSt /user/create HTTP/1.1";
        String headersString = "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n" +
                "Content-Length: 59\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "Accept: */*\r\n";
        String bodyString = "userId=javajigi&password=password&name=자바지기&email=javajigi@slipp.net";

        BufferedReader br = null;
        HttpRequestStartLine httpRequestStartLine = new HttpRequestStartLine(startLine);
        HttpRequestHeaders httpRequestHeaders = new HttpRequestHeaders(headersString);
        HttpRequestBody httpRequestBody = new HttpRequestBody(bodyString);

        HttpRequest request = new HttpRequest(br, httpRequestStartLine, httpRequestHeaders, httpRequestBody);

        Method handler = HandlerMapper.getHandler(request);

        Assertions.assertEquals("createUser", handler.getName());
    }
}
