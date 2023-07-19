package util;

import http.Mime;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class HttpUtilsTest {

    @Test
    @DisplayName("Http version이 1.1이다.")
    void checkHttpVersion1_1() {
        //given
        String version = "HTTP/1.1";

        //when
        HttpClient.Version httpVersion = HttpUtils.getHttpVersion(version).orElseThrow();

        //then
        assertThat(httpVersion).isEqualTo(HttpClient.Version.HTTP_1_1);
    }

    @Test
    @DisplayName("Http version이 2.0이다.")
    void checkHttpVersion2_0() {
        //given
        String version = "HTTP/2.0";

        //when
        HttpClient.Version httpVersion = HttpUtils.getHttpVersion(version).orElseThrow();

        //then
        assertThat(httpVersion).isEqualTo(HttpClient.Version.HTTP_2);
    }

    @Test
    @DisplayName("다른 http 버전이면 빈값을 반환한다.")
    void returnHttpVersionEmpty() {
        //given
        String version = "HTTP/1.0";

        //when
        Optional<HttpClient.Version> httpVersion = HttpUtils.getHttpVersion(version);

        //then
        assertThat(httpVersion).isEqualTo(Optional.empty());
    }

    @Test
    @DisplayName("http 헤더를 파싱해서 map으로 변환한다.")
    void parseHeader() throws IOException {
        //given
        List<String> requestHeader = List.of("Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*");
        BufferedReader bufferedReader = stringListToBufferedReader(requestHeader);

        //when
        Map<String, String> paredHeader = HttpUtils.parseHeader(bufferedReader);

        //then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(paredHeader.get("Host")).isEqualTo("localhost:8080");
            softAssertions.assertThat(paredHeader.get("Connection")).isEqualTo("keep-alive");
            softAssertions.assertThat(paredHeader.get("Accept")).isEqualTo("*/*");
        });
    }

    @Test
    @DisplayName("http body를 추출한다.")
    void parseBody() throws IOException {
        //given
        List<String> requestBody = List.of("name=han&password=password");
        int contentLength = requestBody.get(0).length();
        BufferedReader bufferedReader = stringListToBufferedReader(requestBody);

        //when
        String paredHeader = HttpUtils.parseBody(bufferedReader, contentLength, HttpUtils.Method.POST);

        //then
        assertThat(paredHeader).isEqualTo(requestBody.get(0));
    }

    @Test
    @DisplayName("http method가 get이면 body는 null이다.")
    void parseBodyGetNull() throws IOException {
        //given
        List<String> requestBody = List.of("name=han&password=password");
        int contentLength = requestBody.get(0).length();
        BufferedReader bufferedReader = stringListToBufferedReader(requestBody);

        //when
        String paredHeader = HttpUtils.parseBody(bufferedReader, contentLength, HttpUtils.Method.GET);

        //then
        assertThat(paredHeader).isNull();
    }

    @Test
    @DisplayName("URI를 구성한다.")
    void makeUri() throws URISyntaxException {
        //given
        String host = "localhost:8080";
        String file = "/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        //when
        URI uri = HttpUtils.constructUri(host, file);

        //then
        String expectedUri = "http://localhost:8080/user/create?userId=javajigi&password=password&name=박재성&email=javajigi@slipp.net";
        assertThat(uri.toString()).isEqualTo(expectedUri);
    }

    @Test
    @DisplayName("html 확장자는 HTML MIME을 반환한다.")
    void decideHtml() {
        //given
        String path = "/user/index.html";

        //when
        Mime mime = HttpUtils.decideMime(path);

        //then
        assertThat(mime).isEqualTo(Mime.HTML);
    }

    @Test
    @DisplayName("일치하는 확장자가 없으면 defalt를 반환한다.")
    void decideMime() {
        //given
        String path = "/user/index";

        //when
        Mime mime = HttpUtils.decideMime(path);

        //then
        assertThat(mime).isEqualTo(Mime.DEFAULT);
    }

    private BufferedReader stringListToBufferedReader(List<String> strings) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : strings) {
            stringBuilder.append(str).append(System.lineSeparator());
        }
        String stringData = stringBuilder.toString();
        return new BufferedReader(new StringReader(stringData));
    }
}