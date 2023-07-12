package http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class HttpResponseTest {

    @Test
    @DisplayName("존재하지 않는 파일 경로는 입력하면 예외가 발생한다")
    void createWrongResponse() {
        assertThrows(FileNotFoundException.class, () -> new HttpResponse("/wrong.html"));
    }

    @Test
    @DisplayName("body를 깊은 복사하여 반환한다.")
    void deepCopyBody() throws FileNotFoundException {
        //given
        HttpResponse httpResponse = new HttpResponse("/test.html");

        //when
        byte[] body1 = httpResponse.getBody();
        byte[] body2 = httpResponse.getBody();

        //then
        assertThat(body1 == body2).isFalse();
        assertThat(Arrays.equals(body1, body2)).isTrue();
    }
}
