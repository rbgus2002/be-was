package annotation;

import http.HttpRequest;
import http.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RequestMappingHandlerTest {

    @Test
    @DisplayName("controller class에 있는 index()를 실행한다.")
    void invokeIndex() throws Throwable {
        //given
        List<String> requestLines = List.of("GET /index.html HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*");
        HttpRequest httpRequest = new HttpRequest(requestLines);

        //when
        HttpResponse httpResponse = RequestMappingHandler.invokeMethod(httpRequest);

        //then
        HttpResponse expectedHttpResponse = HttpResponse.ok("/index.html");
        assertThat(httpResponse).usingRecursiveComparison().isEqualTo(expectedHttpResponse);
    }

    @Test
    @DisplayName("@RequestMapping 되지 않은 메소드는 실행하면 예외가 발생한다.")
    void invalidInvokeMethod() throws Throwable {
        //given
        List<String> requestLines = List.of("GET /qpaleorjf.html HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*");
        HttpRequest httpRequest = new HttpRequest(requestLines);

        //when, then
        assertThrows(IllegalAccessException.class, () -> RequestMappingHandler.invokeMethod(httpRequest));
    }
}