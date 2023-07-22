package utils;

import common.enums.RequestMethod;
import common.wrapper.Headers;
import common.http.HttpRequest;
import common.wrapper.Queries;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static common.enums.RequestMethod.GET;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpRequestUtilsTest {

    InputStream in;

    @Test
    @DisplayName("[/] Request Line 파싱")
    void parseRequestLine1() throws IOException {
        String requestMessage = createRequestMessage("GET", "/", "");
        in = new ByteArrayInputStream(requestMessage.getBytes());

        Queries expectedQueries = new Queries();

        HttpRequest request = HttpRequestUtils.createRequest(in);

        verifyRequestLine(request, GET, "/", "", expectedQueries);
    }

    @Test
    @DisplayName("[/index.html] Request Line 파싱")
    void parseRequestLine2() throws IOException {
        String requestMessage = createRequestMessage("GET", "/index.html", "");
        in = new ByteArrayInputStream(requestMessage.getBytes());

        Queries expectedQueries = new Queries();

        HttpRequest request = HttpRequestUtils.createRequest(in);

        verifyRequestLine(request, GET, "/index.html", "", expectedQueries);
    }

    @Test
    @DisplayName("[/dir1/index.html] Request Line 파싱")
    void parseRequestLine3() throws IOException {
        String requestMessage = createRequestMessage("GET", "/dir1/index.html", "");
        in = new ByteArrayInputStream(requestMessage.getBytes());

        Queries expectedQueries = new Queries();

        HttpRequest request = HttpRequestUtils.createRequest(in);

        verifyRequestLine(request, GET, "/dir1/index.html", "", expectedQueries);
    }

    @Test
    @DisplayName("[/dir1/index.html?p1=v1&p2=v2] Request Line 파싱")
    void parseRequestLine4() throws IOException {
        String requestMessage = createRequestMessage("GET", "/dir1/index.html?p1=v1&p2=v2", "");
        in = new ByteArrayInputStream(requestMessage.getBytes());

        Queries expectedQueries = new Queries();
        expectedQueries.addAttribute("p1", "v1");
        expectedQueries.addAttribute("p2", "v2");

        HttpRequest request = HttpRequestUtils.createRequest(in);

        verifyRequestLine(request, GET, "/dir1/index.html", "", expectedQueries);
    }

    @Test
    @DisplayName("[/create?p1=v1&p2=v2] Request Line 파싱")
    void parseRequestLine5() throws IOException {
        String requestMessage = createRequestMessage("GET", "/create?p1=v1&p2=v2", "");
        in = new ByteArrayInputStream(requestMessage.getBytes());

        Queries expectedQueries = new Queries();
        expectedQueries.addAttribute("p1", "v1");
        expectedQueries.addAttribute("p2", "v2");

        HttpRequest request = HttpRequestUtils.createRequest(in);

        verifyRequestLine(request, GET, "/create", "", expectedQueries);
    }

    @Test
    @DisplayName("[/create?p1=&p2=] Request Line 파싱")
    void parseRequestLine6() throws IOException {
        String requestMessage = createRequestMessage("GET", "/create?p1=&p2=", "");
        in = new ByteArrayInputStream(requestMessage.getBytes());

        Queries expectedQueries = new Queries();
        expectedQueries.addAttribute("p1", "");
        expectedQueries.addAttribute("p2", "");

        HttpRequest request = HttpRequestUtils.createRequest(in);

        verifyRequestLine(request, GET, "/create", "", expectedQueries);
    }

    @Test
    @DisplayName("[/] 헤더 파싱")
    void parseHeader() throws IOException{
        String requestMessage = createRequestMessage("GET", "/", "");
        in = new ByteArrayInputStream(requestMessage.getBytes());

        Headers expectedHeader = new Headers();
        expectedHeader.addAttribute("Host", "localhost:8080");
        expectedHeader.addAttribute("Accept", "*/*");

        HttpRequest request = HttpRequestUtils.createRequest(in);

        assertEquals(expectedHeader, request.getHeaders());
    }

    @Test
    @DisplayName("[/] 바디 파싱")
    void parseBody() throws IOException {
        String requestMessage = createRequestMessage("GET", "/", "v1=p1&v2=p2");
        in = new ByteArrayInputStream(requestMessage.getBytes());

        HttpRequest request = HttpRequestUtils.createRequest(in);

        assertEquals("v1=p1&v2=p2", request.getBody());
    }

    @Test
    @DisplayName("[/] POST 바디 파싱후 쿼리 파라미터 확인")
    void parseBody2() throws IOException {
        String requestMessage = createRequestMessage("POST", "/", "v1=p1&v2=p2");
        in = new ByteArrayInputStream(requestMessage.getBytes());

        Queries expectedQueries = new Queries();
        expectedQueries.addAttribute("v1", "p1");
        expectedQueries.addAttribute("v2", "p2");

        HttpRequest request = HttpRequestUtils.createRequest(in);

        assertEquals(expectedQueries, request.getQueries());
    }

    private String createRequestMessage(String method, String uri, String body) {
        return method + " " + uri + " " + "HTTP/1.1\r\n" +
                "Host: localhost:8080\r\n" +
                "Accept: */*\r\n"+
                "\r\n"+
                body;
    }

    private void verifyRequestLine(
            HttpRequest request,
            RequestMethod expectedRequestMethod,
            String expectedPath,
            String expectedBody,
            Queries expectedQueries
    ) {
        assertEquals(expectedRequestMethod, request.getRequestMethod());
        assertEquals(expectedPath, request.getRequestPath());
        assertEquals(expectedBody, request.getBody());
        assertEquals(expectedQueries, request.getQueries());
    }

}
