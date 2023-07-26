package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import support.exception.NotFoundException;
import support.exception.ServerErrorException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("웹 페이지 리더기 테스트")
class WebPageReaderTest {

    @Test
    @DisplayName("Html 파일을 읽어야 한다.")
    void read() throws NotFoundException, ServerErrorException {
        //given
        String url = "/index.html";

        //when
        byte[] page = WebPageReader.readByPath(url);

        //then
        assertNotNull(page);
        assertNotEquals(0, page.length);

    }

    @Test
    @DisplayName("css 파일을 읽어야 한다.")
    void readCss() throws NotFoundException, ServerErrorException {
        //given
        String url = "/css/bootstrap.min.css";

        //when
        byte[] page = WebPageReader.readByPath(url);

        //then
        assertNotNull(page);
        assertNotEquals(0, page.length);
    }

    @Test
    @DisplayName("javascript 파일을 읽어야 한다.")
    void readJs() throws NotFoundException, ServerErrorException {
        //given
        String url = "/js/scripts.js";

        //when
        byte[] page = WebPageReader.readByPath(url);

        //then
        assertNotNull(page);
        assertNotEquals(0, page.length);
    }

    @Test
    @DisplayName("없는 파일은 예외를 발생하며 실패하여야 한다.")
    void readNoFile() {
        //given
        String url = "/NO_FILE";

        //when
        Throwable exception = assertThrows(
                NotFoundException.class,
                () -> WebPageReader.readByPath(url)
        );

        //then
    }

}
