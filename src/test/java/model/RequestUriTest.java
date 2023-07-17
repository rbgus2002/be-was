package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class RequestUriTest {

    @ParameterizedTest
    @ValueSource(strings = {
            "/asdf",
            "/a/b/c?ab=123&parameter=asdf&name=&age=123",
            "/hi/hello?",
            "/?abc=abc&name=kim&age",
            "/name?name=name&kim=&abc&good=1234"
    })
    @DisplayName("path uri의 parameter를 잘 분해한다.")
    void parsingPathUri(String path) {
       RequestUri requestUri = RequestUri.of(path);

        System.out.println(requestUri);
    }
}