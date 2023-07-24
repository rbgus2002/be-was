package webserver.http.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.MIME;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BodyTest {
    @Test
    @DisplayName("content length가 0인, 빈 body를 리턴한다.")
    void emptyBody() {
        // given

        // when
        Body emptyBody = Body.emptyBody();

        // then
        assertAll(() -> {
            assertEquals("", emptyBody.toString());
            assertEquals(MIME.defaultMime().getContentType(), emptyBody.getContentType());
            assertEquals(0, emptyBody.getLength());
        });
    }

    @Test
    @DisplayName("content, mime을 갖는 body를 생성한다.")
    void from() {
        // given
        byte[] content = "바디입니다.".getBytes();
        MIME mime = MIME.TXT;

        // when
        Body body = Body.from(content, mime);

        // then
        assertAll(() -> {
            assertEquals("바디입니다.", body.toString());
            assertEquals(mime.getContentType(), body.getContentType());
            assertEquals(content.length, body.getLength());
        });
    }
}
