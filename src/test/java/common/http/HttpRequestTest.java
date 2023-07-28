package common.http;

import common.enums.ContentType;
import common.enums.RequestMethod;
import common.wrapper.Cookies;
import common.wrapper.Headers;
import common.wrapper.Queries;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static common.enums.RequestMethod.GET;
import static common.enums.RequestMethod.POST;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpRequestTest {

    @Test
    @DisplayName("요청 메서드를 가져올 수 있다.")
    void getRequestMethod() {
        HttpRequest request = createRequest(GET, "/index.html", "key1: value1\r\nkey2: value2", "");
        assertEquals(GET, request.getRequestMethod());
    }

    @Test
    @DisplayName("[/index.html] 요청 경로를 가져올 수 있다.")
    void getRequestPath1() {
        HttpRequest request = createRequest(GET, "/index.html", "key1: value1\r\nkey2: value2", "");
        assertEquals("/index.html", request.getRequestPath());
    }

    @Test
    @DisplayName("[/index.html?q1=v1&q2=v2] 요청 경로를 가져올 수 있다.")
    void getRequestPath2() {
        HttpRequest request = createRequest(GET, "/index.html?q1=v1&q2=v2", "key1: value1\r\nkey2: value2", "");
        assertEquals("/index.html", request.getRequestPath());
    }

    @Test
    @DisplayName("[/index.html?q1=v1&q2=v2] 컨텐츠 타입을 가져올 수 있다.")
    void getContentType1() {
        HttpRequest request = createRequest(GET, "/index.html?q1=v1&q2=v2", "key1: value1\r\nkey2: value2", "");
        assertEquals(ContentType.HTML, request.getRequestContentType());
    }

    @Test
    @DisplayName("[/user/create] 컨텐츠 타입을 가져올 수 있다.")
    void getContentType2() {
        HttpRequest request = createRequest(GET, "/user/create", "key1: value1\r\nkey2: value2", "");
        assertEquals(ContentType.NONE, request.getRequestContentType());
    }

    @Test
    @DisplayName("[GET /index.html?q1=v1&q2=v2] 쿼리 파라미터를 가져올 수 있다.")
    void getQueries1() {
        HttpRequest request = createRequest(GET, "/index.html?q1=v1&q2=v2", "key1: value1\r\nkey2: value2", "");
        assertEquals(new Queries("q1=v1&q2=v2"), request.getQueries());
    }

    @Test
    @DisplayName("[GET /index.html][body: q3=v3&q4=v4] 쿼리 파라미터를 가져올 수 있다.")
    void getQueries2() {
        HttpRequest request = createRequest(GET, "/index.html", "key1: value1\r\nkey2: value2", "q1=v1&q2=v2");
        assertEquals(new Queries("q1=v1&q2=v2"), request.getQueries());
    }

    @Test
    @DisplayName("[GET /index.html?q1=v1&q2=v2][body: q3=v3&q4=v4] 쿼리 파라미터를 가져올 수 있다.")
    void getQueries3() {
        HttpRequest request = createRequest(GET, "/index.html?q1=v1&q2=v2", "key1: value1\r\nkey2: value2", "q3=v3&q4=v4");
        assertEquals(new Queries("q1=v1&q2=v2&q3=v3&q4=v4"), request.getQueries());
    }

    @Test
    @DisplayName("[POST /index.html?q1=v1&q2=v2] 쿼리 파라미터를 가져올 수 있다.")
    void getQueries4() {
        HttpRequest request = createRequest(POST, "/index.html?q1=v1&q2=v2", "key1: value1\r\nkey2: value2", "");
        assertEquals(new Queries("q1=v1&q2=v2"), request.getQueries());
    }

    @Test
    @DisplayName("[POST /index.html][body: q1=v1&q2=v2] 쿼리 파라미터를 가져올 수 있다.")
    void getQueries5() {
        HttpRequest request = createRequest(POST, "/index.html", "key1: value1\r\nkey2: value2", "q1=v1&q2=v2");
        assertEquals(new Queries("q1=v1&q2=v2"), request.getQueries());
    }

    @Test
    @DisplayName("[POST /index.html?q1=v1&q2=v2][body: q3=v3&q4=v4] 쿼리 파라미터를 가져올 수 있다.")
    void getQueries6() {
        HttpRequest request = createRequest(POST, "/index.html?q1=v1&q2=v2", "key1: value1\r\nkey2: value2", "q3=v3&q4=v4");
        assertEquals(new Queries("q1=v1&q2=v2&q3=v3&q4=v4"), request.getQueries());
    }

    @Test
    @DisplayName("쿠키를 가져올 수 있다.")
    void getCookies() {
        HttpRequest request = createRequest(GET, "/index.html?q1=v1&q2=v2", "key1: value1\r\nkey2: value2\r\nCookie: c1=v1", "");
        assertEquals(new Cookies("c1=v1"), request.getCookies());
    }

    @Test
    @DisplayName("body를 가져올 수 있다.")
    void getBody() {
        HttpRequest request = createRequest(GET, "/index.html", "key1: value1\r\nkey2: value2", "helloWorld");
        assertEquals("helloWorld", request.getBody());
    }

    private HttpRequest createRequest(RequestMethod rm, String uri, String headerString, String bodyString) {
        RequestLine requestLine = new RequestLine(rm, new Uri(uri));
        Headers headers = new Headers(headerString);
        RequestBody requestBody = new RequestBody(bodyString);

        return new HttpRequest(requestLine, headers, requestBody);
    }

}