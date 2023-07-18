package webserver.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.StringUtils.NEW_LINE;

@DisplayName("응답 메시지 클래스 테스트")
class ResponseMessageTest {
    @Test
    @DisplayName("응답 메시지를 생성한다.")
    void create() {
        // given
        HttpStatus ok = HttpStatus.OK;
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
                "Content-Length: " + body.length() + NEW_LINE +
                "Content-Type: text/html;charset=utf-8" + NEW_LINE;
        // when
        ResponseMessage message = new ResponseMessage(ok, body.getBytes());

        // then
        assertAll(
                () -> assertEquals(body, new String(message.getResponseBody())),
                () -> assertEquals(header, message.getResponseHeader())
        );
    }
}
