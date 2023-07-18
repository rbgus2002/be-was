package webserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.http.request.HttpRequest;
import webserver.http.request.Uri;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;
import static utils.StringUtils.appendNewLine;

class HandlerMappingTest {
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

    @Test
    @DisplayName("GetMapping 어노테이션이 붙은 메소드를 실행한다")
    void doDispatch() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        // given, when
        HttpRequest request = HttpRequest.from(in);

        // then
//        HandlerMapping handlerMapping = new HandlerMapping();
//        handlerMapping.doDispatch(request);
    }
}