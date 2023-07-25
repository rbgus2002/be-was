package webserver.http;

import http.Uri;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UriTest {
    private final String PATH = "/user/create";
    private final String QUERY = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi@40slipp.net";
    private final String URI = PATH + "?" + QUERY;
    
    @Test
    @DisplayName("쿼리 스트링이 존재하지 않는 Uri 객체를 생성한다")
    void createUriNotExistQueryString(){
        // given, when
        Uri uri = Uri.from(PATH);

        // then
        assertEquals(PATH, uri.toString());
    }

    @Test
    @DisplayName("쿼리 스트링이 존재하는 Uri 객체를 생성한다")
    void createUri(){
        // given, when
        Uri uri = Uri.from(URI);

        // then
        assertEquals(PATH, uri.getPath());
    }
}