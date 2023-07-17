package webserver.http;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class URLTest {
    @DisplayName("URL 객체 생성 테스트")
    @Test
    void getPathTest() {
        String urlString = "/";

        URL url = URL.from(urlString);

        assertEquals(urlString, url.getPath());
    }

    @DisplayName("query Parameter가 있는 URL 객체 생성 테스트")
    @Test
    void getQueryParamTest() {
        String urlString = "/search?param1=1,2&param1=3&param2=a";

        URL url = URL.from(urlString);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(url.getPath()).isEqualTo("/");
        softAssertions.assertThat(url.getQueryParameter()).containsEntry("param1", List.of("1,2,3"));
        softAssertions.assertThat(url.getQueryParameter()).containsEntry("param2", List.of("a"));
    }
}
