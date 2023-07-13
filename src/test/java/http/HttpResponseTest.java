package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class HttpResponseTest {

    @Test
    @DisplayName("존재하지 않는 파일 경로는 입력하면 예외가 발생한다")
    void createWrongResponse() {
        assertThrows(FileNotFoundException.class, () -> HttpResponse.ok("/wrong.html"));
    }
}
