package webserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.request.HttpRequest;
import webserver.http.request.Uri;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;

import static utils.StringUtils.appendNewLine;

class DispatcherServletTest {
    private final String GET = "GET";
    private final String PATH = "/index.html";

    private final Uri URI = Uri.from(PATH);
    private String requestStr;
    private InputStream in;

    @BeforeEach
    void setRequestStr() {
        requestStr = GET + " " + URI + " " + appendNewLine("HTTP/1.1") +
                appendNewLine("Host: localhost:8080") +
                appendNewLine("Connection: keep-alive") +
                appendNewLine("Cache-Control: max-age=0") +
                "";
        in = new ByteArrayInputStream(requestStr.getBytes());
    }
    
//    @Test
//    @DisplayName("컨텐츠 타입이 html인 경우, templates 폴더 안에서 파일을 가져온다")
//    void doDispatchHtmlFile(){
//        // given
//
//
//        // when
//
//        // then
//
//    }
}