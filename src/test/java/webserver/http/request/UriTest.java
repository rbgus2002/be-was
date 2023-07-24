package webserver.http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import webserver.http.MIME;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UriTest {
    @Test
    @DisplayName("넘겨받은 string으로부터 path, query를 분리해 Uri 객체를 생성한다.")
    void from() {
        // given
        String stringUri = "/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        // when
        Uri createdUri = Uri.from(stringUri);

        // then
        assertAll(() -> {
            assertEquals(stringUri, createdUri.toString());
            assertEquals("/user/create", createdUri.getPath());
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"/css/styles.css", "/js/script.js", "favicon.ico"})
    @DisplayName("path의 확장자에 맞는 MIME 타입을 반환해야 한다.")
    void getMime(String stringUri) {
        // given
        Uri uri = Uri.from(stringUri);

        // when
        MIME mime = uri.getMime();

        // then
        assertEquals(MIME.from(stringUri.split("\\.")[1]), mime);
    }

    @Test
    @DisplayName("path에 확장자가 존재하지 않는 경우 default MIME 타입을 리턴해야한다.")
    void getMimeWithoutExtension() {
        // given
        String stringUri = "/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        Uri createUri = Uri.from(stringUri);

        // when
        MIME mime = createUri.getMime();

        // then
        assertEquals(MIME.defaultMime(), mime);
    }
}
