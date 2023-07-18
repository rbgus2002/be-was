package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringUtilsTest {

    @Test
    @DisplayName("html 확장자를 추출한다.")
    void extractHtmlString() {
        //given
        String file = "aa.html";

        //when
        String extension = StringUtils.getExtension(file);

        //then
        assertThat(extension).isEqualTo("html");
    }

    @Test
    @DisplayName("js 확장자를 추출한다.")
    void extractJsString() {
        //given
        String file = "afjknlk/aslkd.js";

        //when
        String extension = StringUtils.getExtension(file);

        //then
        assertThat(extension).isEqualTo("js");
    }
}
