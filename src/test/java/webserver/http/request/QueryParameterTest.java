package webserver.http.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("쿼리 파라미터 테스트")
class QueryParameterTest {
    @DisplayName("문자열로부터 쿼리 파라미터를 추출한다.")
    @Test
    void from() {
        String url = "/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        QueryParameter queryParameter = QueryParameter.from(url);

        assertAll(
                () -> assertThat(queryParameter.get("userId")).isEqualTo("javajigi"),
                () -> assertThat(queryParameter.get("password")).isEqualTo("password"),
                () -> assertThat(queryParameter.get("name")).isEqualTo("박재성"),
                () -> assertThat(queryParameter.get("email")).isEqualTo("javajigi@slipp.net")
        );
    }
}
