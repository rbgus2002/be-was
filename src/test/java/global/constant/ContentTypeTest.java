package global.constant;

import exception.NotFoundExtensionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ContentTypeTest {
    @Test
    @DisplayName("html 확장자 요청 시, 정상적으로 content type을 반환한다.")
    public void testFindContentTypeWithValidExtension() throws Exception {
        //given
        String fileType = "html";

        //when
        ContentType contentType = ContentType.findContentType(fileType);

        //then
        assertEquals(ContentType.HTML.getContentType(), contentType.getContentType());
    }


    @Test
    @DisplayName("적절하지 않은 파일 확장자 입력 시, 에러를 반환한다.")
    public void testFindContentTypeWithInvalidExtension() throws Exception {
        //given
        String fileType = "invalid";

        //when&then
        assertThrows(NotFoundExtensionException.class, () -> ContentType.findContentType(fileType));
    }

    @Test
    @DisplayName("getContentType()을 검증한다.")
    public void testGetContentType() {
        //given
        ContentType contentType = ContentType.HTML;

        //when&then
        assertEquals(ContentType.HTML.getContentType(), contentType.getContentType());
    }
}
