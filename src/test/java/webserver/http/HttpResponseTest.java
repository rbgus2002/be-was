package webserver.http;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpResponseTest {
    @Test
    @DisplayName("HTTP 응답 헤더를 작성할 수 있어야 한다")
    void createMessage() {
        //given
        HttpResponse httpResponse = new HttpResponse();

        String responseMessage = "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/html;charset=utf-8\r\n" +
                "Content-Length: 0\r\n" +
                "Authorization: Bearer ABCD1234\r\n";

        //when
        httpResponse.setStatus(HttpStatus.OK);
        httpResponse.setHeader(HttpConstant.CONTENT_TYPE, "text/html;charset=utf-8");
        httpResponse.setHeader(HttpConstant.CONTENT_LENGTH, "0");
        httpResponse.setHeader(HttpConstant.AUTHORIZATION, "Bearer ABCD1234");

        //then
        Assertions.assertEquals(responseMessage, httpResponse.getHeaderMessage());
    }

}