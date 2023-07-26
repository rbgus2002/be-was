package webserver.http.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.HttpStatusCode;
import webserver.http.MIME;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.StringUtils.CRLF;
import static utils.StringUtils.appendNewLine;

class HttpResponseTest {
    @Test
    @DisplayName("Body가 있는 response를 생성해야한다.")
    void createResponseWithBody() {
        // given
        String version = "HTTP/1.1";
        HttpStatusCode statusCode = HttpStatusCode.OK;
        byte[] content = "바디입니다.".getBytes();
        ResponseBody body = ResponseBody.from(content, MIME.TXT);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(appendNewLine(version + " " + statusCode.toString()));
        stringBuilder.append(appendNewLine("Content-Type: " + MIME.TXT.getContentType()));
        stringBuilder.append(appendNewLine("Content-Length: " + body.getLength()));
        stringBuilder.append(CRLF);
        stringBuilder.append(body.toString());

        // when
        HttpResponse response = HttpResponse.of(version, statusCode, body);

        // then
        assertEquals(stringBuilder.toString(), response.toString());
    }

    @Test
    @DisplayName("Body가 없는(EmptyBody를 가지는) response를 생성해야한다.")
    void createResponseWithoutBody() {
        // given
        String version = "HTTP/1.1";
        HttpStatusCode statusCode = HttpStatusCode.OK;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(appendNewLine(version + " " + statusCode.toString()));
        stringBuilder.append(appendNewLine("Content-Type: " + MIME.defaultMime().getContentType()));
        stringBuilder.append(appendNewLine("Content-Length: " + 0));
        stringBuilder.append(CRLF);

        // when
        HttpResponse response = HttpResponse.of(version, statusCode);

        // then
        assertEquals(stringBuilder.toString(), response.toString());
    }
}
