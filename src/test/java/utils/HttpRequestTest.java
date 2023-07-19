package utils;

import http.HttpRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.StringUtil;

import java.io.*;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static util.StringUtil.*;

class HttpRequestTest {

    @Test
    @DisplayName("HttpRequest 테스트")
    void test() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(appendNewLine("GET /index.html HTTP/1.1"))
                .append(appendNewLine("Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7"))
                .append(appendNewLine("Connection: keep-alive"));
        InputStream in = new ByteArrayInputStream(sb.toString().getBytes());

        HttpRequest httpRequest = new HttpRequest(in);

        String method = httpRequest.getMethod();
        String path = httpRequest.getPath();
        String version = httpRequest.getVersion();
        Map<String, String> headers = httpRequest.getHeaders();

        assertThat(method).isEqualTo("GET");
        assertThat(path).isEqualTo("/index.html");
        assertThat(version).isEqualTo("HTTP/1.1");
        assertThat(headers).hasSize(2)
                .contains(entry("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7"))
                .contains(entry("Connection", "keep-alive"));
    }
}