package util;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpUtilTest {

    @Test
    @DisplayName("버퍼를 요청하는데 성공한다.")
    public void getBuffersTest() throws IOException {
        //given
        String input = "Hello, World!";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());

        //when
        String result = HttpUtil.getBuffers(inputStream);

        //then
        assertEquals(input + System.lineSeparator(), result);
    }

    @Test
    @DisplayName("파일 url을 가져오는데 성공한다.")
    public void getUrlTest() throws Exception {
        //given
        String content = "GET /example.html HTTP/1.1";

        //when
        String result = HttpUtil.getUrl(content);

        //then
        assertEquals("/example.html", result);
    }

    @Test
    @DisplayName("정상적인 html 파일 포멧인지 검증한다.")
    public void isFileRequestTest() {
        //given
        SoftAssertions softAssertions = new SoftAssertions();

        //when&then
        softAssertions.assertThat(HttpUtil.isFileRequest("index.html")).as("단일 파일 포멧").isEqualTo(true);
        softAssertions.assertThat(HttpUtil.isFileRequest("path/to/file.html")).as("복합 파일 포멧").isEqualTo(true);
        softAssertions.assertThat(HttpUtil.isFileRequest("path/to/file.js")).as("html 이외의 포멧").isEqualTo(false);
        softAssertions.assertThat(HttpUtil.isFileRequest("path/to/folder")).as("폴더 경로").isEqualTo(false);
    }
}