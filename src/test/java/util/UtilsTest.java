package util;

import model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UtilsTest {

    @Test
    @DisplayName("Http version이 1.1이다.")
    void checkHttpVersion1_1() {
        //given
        String version = "HTTP/1.1";

        //when
        HttpClient.Version httpVersion = Utils.getHttpVersion(version).orElseThrow();

        //then
        assertThat(httpVersion).isEqualTo(HttpClient.Version.HTTP_1_1);
    }

    @Test
    @DisplayName("Http version이 2.0이다.")
    void checkHttpVersion2_0() {
        //given
        String version = "HTTP/2.0";

        //when
        HttpClient.Version httpVersion = Utils.getHttpVersion(version).orElseThrow();

        //then
        assertThat(httpVersion).isEqualTo(HttpClient.Version.HTTP_2);
    }

    @Test
    @DisplayName("다른 http 버전이면 빈값을 반환한다.")
    void returnHttpVersionEmpty() {
        //given
        String version = "HTTP/1.0";

        //when
        Optional<HttpClient.Version> httpVersion = Utils.getHttpVersion(version);

        //then
        assertThat(httpVersion).isEqualTo(Optional.empty());
    }

    @Test
    @DisplayName("bufferedReader를 list로 변환한다.")
    void convertBufferedReaderToList() throws IOException {
        //given
        String newLine = System.getProperty("line.separator");
        InputStream inputStream = new ByteArrayInputStream(("a" + newLine + "bb" + newLine + "ccc").getBytes());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        //when
        List<String> strings = Utils.convertBufferedReaderToList(bufferedReader);

        //then
        List<String> expected = List.of("a", "bb", "ccc");
        assertThat(strings).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    @DisplayName("리플렉션을 이용해 쿼리값을 User 생성자의 인자로 넣는다.")
    void makeUserWithReflection() {
        //given
        Map<String, String> queries = new HashMap<>();
        queries.put("name", "han");
        queries.put("email", "a@a.com");
        queries.put("userId", "1");
        queries.put("password", "1q2w3e4r");

        //when
        User user = Utils.createUserFromQueryParameters(User.class, queries);

        //then
        assertThat(user.getName()).isEqualTo(queries.get("name"));
        assertThat(user.getEmail()).isEqualTo(queries.get("email"));
        assertThat(user.getUserId()).isEqualTo(queries.get("userId"));
        assertThat(user.getPassword()).isEqualTo(queries.get("password"));
    }

    @Test
    @DisplayName("map 사이즈가 더 크면 생성자 리플렉션에 실패한다.")
    void failMapLengthLarger() {
        //given
        Map<String, String> queries = new HashMap<>();
        queries.put("name", "han");
        queries.put("email", "a@a.com");
        queries.put("userId", "1");
        queries.put("password", "1q2w3e4r");
        queries.put("a", "b");

        //when, then
        assertThrows(IllegalArgumentException.class, () -> Utils.createUserFromQueryParameters(User.class, queries));
    }

    @Test
    @DisplayName("필드 변수 일치하지 않으면 생성자 리플렉션에 실패한다.")
    void parameterNotMath() {
        //given
        Map<String, String> queries = new HashMap<>();
        queries.put("name", "han");
        queries.put("email", "a@a.com");
        queries.put("userId", "1");
        queries.put("a", "1q2w3e4r");

        //when, then
        assertThrows(IllegalArgumentException.class, () -> Utils.createUserFromQueryParameters(User.class, queries));
    }
}
