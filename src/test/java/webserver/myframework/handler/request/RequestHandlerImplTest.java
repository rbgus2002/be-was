package webserver.myframework.handler.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import static org.assertj.core.api.Assertions.*;


@DisplayName("RequestHandlerImpl 테스트")
class RequestHandlerImplTest {
    @Nested
    @DisplayName("handle method")
    class Handle {
        @Test
        @DisplayName("필드의 메소드를 호출하고 결과로 문자열을 반환한다")
        void invokeMethodOfFieldAndReturnString() throws ReflectiveOperationException {
            //given
            RequestHandlerImpl requestHandler = new RequestHandlerImpl(new TestClass(),
                    TestClass.class.getDeclaredMethod("testMethod", HttpRequest.class, HttpResponse.class));

            //when
            String result = requestHandler.handle(HttpRequest.builder().build(), HttpResponse.getInstance());

            //then
            assertThat(result).isEqualTo("string");
        }
    }

    @SuppressWarnings("SameReturnValue")
    static class TestClass {
        @SuppressWarnings("unused")
        String testMethod(HttpRequest httpRequest, HttpResponse httpResponse) {
            return "string";
        }
    }
}
