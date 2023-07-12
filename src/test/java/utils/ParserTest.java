package utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static utils.Parser.getPathFromRequestLine;

class ParserTest {

    @Test
    @DisplayName("Request line 으로부터 path 를 분리한다")
    void getPath() {
        String requestLine1 = "GET /favicon.ico HTTP/1.1";
        Assertions.assertThat(getPathFromRequestLine(requestLine1)).isEqualTo("/favicon.ico");

        String requestLine2 = "GET /index.html HTTP/1.1";
        Assertions.assertThat(getPathFromRequestLine(requestLine2)).isEqualTo("/index.html");
    }
}