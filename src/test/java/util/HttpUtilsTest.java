package util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
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
}