package webserver.reponse;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class HttpResponseTest {

    HttpResponse httpResponse;

    String header = "HTTP/1.1 200 OK\r\n" +
            "Host: localhost:8080\r\n";

    String headerWithContent = "HTTP/1.1 200 OK\r\n" +
            "Content-Length: 11\r\n" +
            "Content-Type: text/plain;charset=utf-8\r\n";
    @BeforeEach
    void init() {
        httpResponse = new HttpResponse();
    }

    @Test
    @DisplayName("response 헤더명과 헤더값을 넣으면 이를 key, value 쌍으로 저장해야 한다.")
    void header() {
        httpResponse.setStatus(HttpResponseStatus.STATUS_200);
        httpResponse.setHeader("Host", "localhost:8080");

        assertEquals(header, httpResponse.getHttpResponseHeader());
    }

    @Test
    @DisplayName("response에 body값을 넣으면 body를 byte[] 형태로 저장하고 그에 맞는 타입과 길이를 헤어데 저장해야 한다.")
    void body(){
        httpResponse.setStatus(HttpResponseStatus.STATUS_200);
        httpResponse.setBodyByText("Hello World");

        assertTrue(Arrays.equals("Hello World".getBytes(), httpResponse.getHttpResponseBody()));
        assertEquals(headerWithContent, httpResponse.getHttpResponseHeader());
    }
}