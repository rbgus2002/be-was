package http;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HttpHeaderTest {

    @Test
    @DisplayName("http 헤더를 파싱해서 HttpHeader를 생성한다.")
    void parseHeader() throws IOException {
        //given
        List<String> requestHeader = List.of("Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*");
        BufferedReader bufferedReader = stringListToBufferedReader(requestHeader);

        //when
        HttpHeader httpHeader = new HttpHeader(bufferedReader);

        //then
        Map<String, String> headers = httpHeader.getHeaders();
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(headers.get("Host")).isEqualTo("localhost:8080");
            softAssertions.assertThat(headers.get("Connection")).isEqualTo("keep-alive");
            softAssertions.assertThat(headers.get("Accept")).isEqualTo("*/*");
        });
    }

    @Test
    @DisplayName("헤더를 수정할 수 없다.")
    void cannotChangeHeader() throws IOException {
        //given
        List<String> requestHeader = List.of("Host: localhost:8080",
                "Connection: keep-alive",
                "Accept: */*");
        BufferedReader bufferedReader = stringListToBufferedReader(requestHeader);

        //when
        HttpHeader httpHeader = new HttpHeader(bufferedReader);

        //then
        Map<String, String> headers = httpHeader.getHeaders();
        assertThatThrownBy(() -> headers.put("a", "b")).isInstanceOf(UnsupportedOperationException.class);
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