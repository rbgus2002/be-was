package annotation;

import http.HttpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class RequestMappingHandlerTest {

    @Test
    @DisplayName("@RequestMapping 되지 않은 메소드는 실행하면 예외가 발생한다.")
    void invalidInvokeMethod() throws Throwable {
        //given
        List<String> requestMessage = List.of("GET /qpaleorjf.html HTTP/1.1",
                "Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*");
        BufferedReader bufferedReader = stringListToBufferedReader(requestMessage);
        HttpRequest httpRequest = new HttpRequest(bufferedReader);

        //when, then
        assertThrows(IllegalAccessException.class, () -> RequestMappingHandler.invokeMethod(httpRequest));
    }

    private BufferedReader stringListToBufferedReader(List<String> strings) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : strings) {
            stringBuilder.append(str).append(System.lineSeparator());
        }
        String stringData = stringBuilder.toString();
        return new BufferedReader(new StringReader(stringData));
    }
}