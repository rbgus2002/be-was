package global.constant;

import exception.NotFoundExtensionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExtensionTest {

    @Test
    @DisplayName("유효한 파일 유형에 대해 올바른 Extension을 반환한다")
    public void testFindExtensionTypeWithValidType() throws Exception {
        //given
        String fileType = "html";

        //when
        Extension extension = Extension.findExtensionType(fileType);

        //then
        assertEquals(Extension.HTML, extension);

    }

    @Test
    @DisplayName("유효하지 않은 파일 유형에 대해 Exception이 발생한다")
    public void testFindExtensionTypeWithInvalidType() throws Exception {
        //given
        String fileType = "invalid";

        //when&then
        assertThrows(NotFoundExtensionException.class, () -> Extension.findExtensionType(fileType));
    }
}
