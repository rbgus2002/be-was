package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import webserver.WebPageReader;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class WebPageReaderTest {


    @Test
    @DisplayName("Html 파일을 읽어야 한다.")
    void read() throws IOException {
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
    void readCss() throws IOException {
        //given
        String url = "/css/bootstrap.min.css";

        //when
        byte[] page = WebPageReader.readByPath(url);

        //then
        assertNotNull(page);
        assertNotEquals(0, page.length);

    }
}
