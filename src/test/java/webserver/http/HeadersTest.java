package webserver.http;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.LinkedHashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HeadersTest {
    @DisplayName("toString 테스트")
    @Test
    void toStringTest() {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put(Http.Headers.CONTENT_LENGTH.getName(), "32");
        map.put("custom", "custom");
        Headers headers = new Headers(map);

        assertThat("Content-Length: 32\r\ncustom: custom").isEqualTo(headers.toString());
    }
}
