package webserver.http.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("HttpRequest body test")
class HttpRequestBodyTest {

    @Test
    @DisplayName("body 문자열을 Map으로 파싱해야 한다.")
    void parseRequestBody() {
        String bodyString = "userId=javajigi&password=password&name=자바지기&email=javajigi@slipp.net";

        HttpRequestBody httpRequestBody = new HttpRequestBody(bodyString);

        assertEquals("javajigi", httpRequestBody.getBody().get("userId"));
        assertEquals("password", httpRequestBody.getBody().get("password"));
        assertEquals("자바지기", httpRequestBody.getBody().get("name"));
        assertEquals("javajigi@slipp.net", httpRequestBody.getBody().get("email"));
    }
}
