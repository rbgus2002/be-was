package webserver.myframework.requesthandler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.*;


@DisplayName("RequestInfoBuilderImpl 테스트")
class RequestInfoBuilderImplTest {
    RequestInfo.Builder builder;

    @BeforeEach
    void setUp() {
        builder = RequestInfo.builder();
    }

    @Nested
    @DisplayName("uri method")
    class Uri {
        @Test
        @DisplayName("uri 필드에 주어진 값을 저장한다")
        void saveGivenValueInUriField() throws ReflectiveOperationException {
            //given
            //when
            builder.uri("/test");

            //then
            Object uri = getField("uri");
            assertThat(uri).isEqualTo("/test");
        }
    }

    @Nested
    @DisplayName("httpMethod method")
    class HttpMethod {
        @Test
        @DisplayName("httpMethod 필드에 주어진 값을 저장한다")
        void saveGivenValueInHttpMethodField() throws ReflectiveOperationException {
            //given
            //when
            builder.httpMethod(webserver.http.HttpMethod.GET);

            //then
            Object httpMethod = getField("httpMethod");
            assertThat(httpMethod).isEqualTo(webserver.http.HttpMethod.GET);
        }
    }

    @Nested
    @DisplayName("build method")
    class Build {
        @Test
        @DisplayName("주어진 값을 통해 RequestInfo 객체를 생성한다")
        void createNewRequestInfoInstanceByGivenValues() {
            //given
            //when
            RequestInfo requestInfo = builder.uri("/test")
                    .httpMethod(webserver.http.HttpMethod.GET)
                    .build();

            //then
            assertThat(requestInfo.isUri("/test"))
                    .isTrue();
            assertThat(requestInfo.isHttpMethod(webserver.http.HttpMethod.GET))
                    .isTrue();
        }
    }

    private Object getField(String fieldName) throws ReflectiveOperationException {
        Field httpMethodField = builder.getClass().getDeclaredField(fieldName);
        httpMethodField.setAccessible(true);
        return httpMethodField.get(builder);
    }
}
