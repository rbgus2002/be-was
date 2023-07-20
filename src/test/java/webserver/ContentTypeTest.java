package webserver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static webserver.ContentType.*;

class ContentTypeTest {
    @Test
    @DisplayName("file에 해당하는 ContentType을 가져온다")
    void findBy(){
        // given
        final String request = "/index.html";

        // when
        ContentType type = ContentType.findBy(request);

        // then
        assertEquals(HTML, type);
    }

    @Test
    @DisplayName("지원하지 않는 ContentType이면 NONE을 가져온다")
    void findNone(){
        // given
        final String request = "/hard.exe";

        // when
        ContentType type = ContentType.findBy(request);

        // then
        assertEquals(NONE, type);
    }

    @Test
    @DisplayName("HTML 파일의 폴더 위치를 매핑한다")
    void mapHTML(){
        // given
        final String request = "/index.html";
        final ContentType html = HTML;

        // when
        final String filePath = html.mapResourceFolders(request);

        // then
        assertEquals(HTML.getPath() + request, filePath);
    }

    @Test
    @DisplayName("CSS 파일의 폴더 위치를 매핑한다")
    void mapCSS(){
        // given
        final String request = "/index.css";
        final ContentType css = CSS;

        // when
        final String filePath = css.mapResourceFolders(request);

        // then
        assertEquals(CSS.getPath() + request, filePath);
    }

    @Test
    @DisplayName("JS 파일의 폴더 위치를 매핑한다")
    void mapJS(){
        // given
        final String request = "/index.js";
        final ContentType js = JS;

        // when
        final String filePath = js.mapResourceFolders(request);

        // then
        assertEquals(JS.getPath() + request, filePath);
    }

    @Test
    @DisplayName("ICO 파일의 폴더 위치를 매핑한다")
    void mapICO(){
        // given
        final String request = "/index.ico";
        final ContentType ico = ICO;

        // when
        final String filePath = ico.mapResourceFolders(request);

        // then
        assertEquals(ICO.getPath() + request, filePath);
    }


    @Test
    @DisplayName("PNG 파일의 폴더 위치를 매핑한다")
    void mapPNG(){
        // given
        final String request = "/index.png";
        final ContentType png = PNG;

        // when
        final String filePath = png.mapResourceFolders(request);

        // then
        assertEquals(PNG.getPath() + request, filePath);
    }


    @Test
    @DisplayName("JPG 파일의 폴더 위치를 매핑한다")
    void mapJPG(){
        // given
        final String request = "/index.jpg";
        final ContentType jpg = JPG;

        // when
        final String filePath = jpg.mapResourceFolders(request);

        // then
        assertEquals(JPG.getPath() + request, filePath);
    }
}