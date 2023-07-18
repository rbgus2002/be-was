package utils;

import common.HttpRequest;
import common.enums.Method;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static common.enums.Method.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpRequestUtilsTest {

    InputStream in;

    @Test
    @DisplayName("[/] Request Line 파싱")
    void parseRequestLine1() throws IOException {
        String requestMessage = createRequestMessage("GET", "/", "");
        in = new ByteArrayInputStream(requestMessage.getBytes());

        HttpRequest request = HttpRequestUtils.createRequest(in);

        verifyRequestLine(request, GET, "/", "", new HashMap<>());
    }

    @Test
    @DisplayName("[/index.html] Request Line 파싱")
    void parseRequestLine2() throws IOException {
        String requestMessage = createRequestMessage("GET", "/index.html", "");
        in = new ByteArrayInputStream(requestMessage.getBytes());

        HttpRequest request = HttpRequestUtils.createRequest(in);

        verifyRequestLine(request, GET, "/index.html", "", new HashMap<>());
    }

    @Test
    @DisplayName("[/dir1/index.html] Request Line 파싱")
    void parseRequestLine3() throws IOException {
        String requestMessage = createRequestMessage("GET", "/dir1/index.html", "");
        in = new ByteArrayInputStream(requestMessage.getBytes());

        HttpRequest request = HttpRequestUtils.createRequest(in);

        verifyRequestLine(request, GET, "/dir1/index.html", "", new HashMap<>());
    }

    @Test
    @DisplayName("[/dir1/index.html?p1=v1&p2=v2] Request Line 파싱")
    void parseRequestLine4() throws IOException {
        String requestMessage = createRequestMessage("GET", "/dir1/index.html?p1=v1&p2=v2", "");
        in = new ByteArrayInputStream(requestMessage.getBytes());

        HttpRequest request = HttpRequestUtils.createRequest(in);

        HashMap<String, String> expectedParams = new HashMap<>();
        expectedParams.put("p1", "v1");
        expectedParams.put("p2", "v2");
        verifyRequestLine(request, GET, "/dir1/index.html", "", expectedParams);
    }

    @Test
    @DisplayName("[/create?p1=v1&p2=v2] Request Line 파싱")
    void parseRequestLine5() throws IOException {
        String requestMessage = createRequestMessage("GET", "/create?p1=v1&p2=v2", "");
        in = new ByteArrayInputStream(requestMessage.getBytes());

        HttpRequest request = HttpRequestUtils.createRequest(in);

        HashMap<String, String> expectedParams = new HashMap<>();
        expectedParams.put("p1", "v1");
        expectedParams.put("p2", "v2");
        verifyRequestLine(request, GET, "/create", "", expectedParams);
    }

    @Test
    @DisplayName("[/create?p1=&p2=] Request Line 파싱")
    void parseRequestLine6() throws IOException {
        String requestMessage = createRequestMessage("GET", "/create?p1=&p2=", "");
        in = new ByteArrayInputStream(requestMessage.getBytes());

        HttpRequest request = HttpRequestUtils.createRequest(in);

        HashMap<String, String> expectedParams = new HashMap<>();
        expectedParams.put("p1", null);
        expectedParams.put("p2", null);
        verifyRequestLine(request, GET, "/create", "", expectedParams);
    }

    private String createRequestMessage(String method, String uri, String body) {
        return method + " " + uri + " " + "HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Connection: keep-alive\r\n"+
                "Accept: */*\r\n"+
                "\r\n"+
                body;
    }

    private void verifyRequestLine(
            HttpRequest request,
            Method expectedMethod,
            String expectedPath,
            String expectedBody,
            Map<String, String> expectedParams
    ) {
        assertEquals(expectedMethod, request.getMethod());
        assertEquals(expectedPath, request.getPath());
        assertEquals(expectedBody, request.getBody());
        assertEquals(expectedParams, request.getParams());
    }
}