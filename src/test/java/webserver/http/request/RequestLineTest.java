package webserver.http.request;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import webserver.http.Http.Method;
import webserver.http.Http.Version;

class RequestLineTest {

    @Test
    void toStringTest() {
        RequestLine requestLine = new RequestLine(Method.POST, new RequestTarget("/index.html"), Version.HTTP_1_1);

        assertThat(requestLine.toString()).isEqualTo("POST /index.html HTTP/1.1");
    }
}
