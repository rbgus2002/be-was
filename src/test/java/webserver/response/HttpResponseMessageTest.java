package webserver.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.constant.HttpStatus;
import webserver.http.response.HttpResponseMessage;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.StringUtils.NEW_LINE;

@DisplayName("응답 메시지 클래스 테스트")
class HttpResponseMessageTest {
    @Test
    @DisplayName("응답 메시지를 생성한다.")
    void create() {
        // given
        String body = "<!DOCTYPE html>" + NEW_LINE +
                "<html>" + NEW_LINE +
                "<head>" + NEW_LINE +
                "  <title>ddingmin Page</title>" + NEW_LINE +
                "</head>" + NEW_LINE +
                "<body>" + NEW_LINE +
                "  <h1>Welcome to the ddinming Page</h1>" + NEW_LINE +
                "  <p>Hello!</p>" + NEW_LINE +
                "</body>" + NEW_LINE +
                "</html>";
        String header = "HTTP/1.1 200 OK" + NEW_LINE +
                "Content-Length: " + body.length() + NEW_LINE;
        // when
        HttpResponseMessage message = new HttpResponseMessage();
        message.setStatusLine(HttpStatus.OK);
        message.setBody(body);

        // then
        assertAll(
                () -> assertEquals(body, new String(message.getResponseBody())),
                () -> assertEquals(header, message.getResponseHeader())
        );
    }
}
