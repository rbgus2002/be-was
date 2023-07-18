package webserver.http;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HttpResponseTest {
    @Test
    @DisplayName("HTTP Response 메시지를 생성할 수 있어야 한다")
    void createMessage() {
        //given
        String body = "http response message body";

        String responseMessage = "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/html;charset=utf-8\r\n" +
                "Content-Length: 0\r\n" +
                "Authorization: Bearer ABCD1234\r\n" +
                "\r\n";

        //when
        HttpResponse httpResponse = new HttpResponse();
        httpResponse.setStatus(HttpStatus.OK);
        httpResponse.setHeader(HttpConstant.CONTENT_TYPE, "text/html;charset=utf-8");
        httpResponse.setHeader(HttpConstant.CONTENT_LENGTH, "0");
        httpResponse.setHeader(HttpConstant.AUTHORIZATION, "Bearer ABCD1234");

        //then
        Assertions.assertEquals(responseMessage, httpResponse.getMessage());
    }

}