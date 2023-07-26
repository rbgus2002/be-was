package webserver.handler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static webserver.handler.HttpBodyParser.parseBodyByContentType;

@DisplayName("Body 파서 클래스 테스트")
class HttpBodyParserTest {
    @Test
    @DisplayName("x-www-form-urlencoded 형태를 파싱한다.")
    void parseUrl() {
        // given
        String xWwwFormUrlEncoded = "userId=javajigi&password=password&name=박재성&email=javajigi@slipp.net";

        // when
        Map<String, String> parsed = parseBodyByContentType("application/x-www-form-urlencoded", xWwwFormUrlEncoded);

        // then
        Assertions.assertAll(
                () -> Assertions.assertTrue(parsed.containsKey("userId")),
                () -> Assertions.assertTrue(parsed.containsKey("password")),
                () -> Assertions.assertTrue(parsed.containsKey("name")),
                () -> Assertions.assertTrue(parsed.containsKey("email")),
                () -> Assertions.assertEquals("javajigi", parsed.get("userId")),
                () -> Assertions.assertEquals("password", parsed.get("password")),
                () -> Assertions.assertEquals("박재성", parsed.get("name")),
                () -> Assertions.assertEquals("javajigi@slipp.net", parsed.get("email"))
        );
    }
}
