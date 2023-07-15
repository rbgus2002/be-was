package webserver.http;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.*;

@DisplayName("HttpRequestBuilderImpl 테스트")
class HttpRequestBuilderImplTest {
    HttpRequest.Builder builder;

    @BeforeEach
    void setup() {
        builder = new HttpRequestBuilderImpl();
    }

    @Nested
    @DisplayName("method method")
    class method {
        @Test
        @DisplayName("method 필드가 저장된 빌더를 반환한다")
        void returnBuilderSetMethodField() throws NoSuchFieldException, IllegalAccessException {
            //given
            //when
            HttpRequest.Builder resultBuilder = builder.method(HttpMethod.DELETE);

            //then
            Field methodField = resultBuilder.getClass().getDeclaredField("method");
            methodField.setAccessible(true);
            assertThat(methodField.get(resultBuilder)).isEqualTo(HttpMethod.DELETE);
        }
    }

    @Nested
    @DisplayName("uri method")
    class Uri {
        @Test
        @DisplayName("uri 필드가 저장된 빌더를 반환한다")
        void returnBuilderSetUriField() throws NoSuchFieldException, IllegalAccessException {
            //given
            //when
            HttpRequest.Builder resultBuilder = builder.uri("/index.html");

            //then
            Field uriField = resultBuilder.getClass().getDeclaredField("uri");
            uriField.setAccessible(true);
            assertThat(uriField.get(resultBuilder)).isEqualTo("/index.html");
        }
    }

    @Nested
    @DisplayName("version method")
    class Version {
        @Test
        @DisplayName("version 필드가 저장된 빌더를 반환한다")
        void returnBuilderSetUriField() throws NoSuchFieldException, IllegalAccessException {
            //given
            //when
            HttpRequest.Builder resultBuilder = builder.version("1.1");

            //then
            Field versionField = resultBuilder.getClass().getDeclaredField("version");
            versionField.setAccessible(true);
            assertThat(versionField.get(resultBuilder)).isEqualTo("1.1");
        }
    }

    @Nested
    @DisplayName("headers method")
    class Headers {
        @Test
        @DisplayName("headers 필드가 저장된 빌더를 반환한다")
        void returnBuilderSetVersionField() throws NoSuchFieldException, IllegalAccessException {
            //given
            //when
            HttpRequest.Builder resultBuilder = builder.addHeader("header1", "header1");

            //then
            Field headersField = resultBuilder.getClass().getDeclaredField("headers");
            headersField.setAccessible(true);

            Object headers = headersField.get(resultBuilder);
            assertThat(headers).isInstanceOf(HttpHeaders.class);
            assertThat(((HttpHeaders) headers).getHeaderValues("header1")).isEqualTo("header1");
        }
    }

    @Nested
    @DisplayName("build method")
    class Build {
        @Test
        @DisplayName("지금까지 지정한 내용을 통해 HttpRequest 객체를 생성하고 반환한다")
        void returnHttpRequest() {
            //given
            builder.method(HttpMethod.GET);
            builder.uri("/index.html");
            builder.version("1.1");
            builder.addHeader("header1", "header1");

            //when
            HttpRequest httpRequest = builder.build();

            //then
            assertThat(httpRequest.getMethod()).isEqualTo(HttpMethod.GET);
            assertThat(httpRequest.getUri()).isEqualTo("/index.html");
            assertThat(httpRequest.getVersion()).isEqualTo("1.1");
            assertThat(httpRequest.getHeader("header1")).isEqualTo("header1");
        }
    }
}
