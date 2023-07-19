package util;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

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

    @Test
    @DisplayName("쿼리 파라미터를 Map으로 변환한다.")
    void convertQueryToMap() {
        //given
        String query = "userId=javajigi&password=password&name=박재성&email=javajigi@slipp.net";

        //when
        Map<String, String> parameters = StringUtils.parseParameters(query);

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(parameters.get("userId")).isEqualTo("javajigi");
            softAssertions.assertThat(parameters.get("password")).isEqualTo("password");
            softAssertions.assertThat(parameters.get("name")).isEqualTo("박재성");
            softAssertions.assertThat(parameters.get("email")).isEqualTo("javajigi@slipp.net");
        });

    }

    @Test
    @DisplayName("쿼리스트링이 없으면 빈 맵을 반환한다.")
    void returnEmptyMapIfQueryNotExist() throws URISyntaxException, IOException {
        //given
        String empty = "";

        //when
        Map<String, String> parameters = StringUtils.parseParameters(empty);

        //then
        assertThat(parameters).isEmpty();
    }
}
