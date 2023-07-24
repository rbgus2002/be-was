package webserver.myframework.handler.argument;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.myframework.handler.argument.annotation.RequestBody;
import webserver.myframework.handler.argument.annotation.RequestParam;
import webserver.myframework.session.SessionManagerImpl;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.*;


@DisplayName("ArgumentResolver 테스트")
class ArgumentResolverImplTest {
    ArgumentResolver argumentResolver = new ArgumentResolverImpl();

    @Nested
    @DisplayName("resolve method")
    class Resolve {
        @Test
        @DisplayName("메소드를 호출하는데 필요한 파라미터들을 반환한다")
        void returnParametersForInvokeMethod() throws NoSuchMethodException {
            //given
            HttpRequest httpRequest = HttpRequest.builder(new SessionManagerImpl())
                    .addParameter("requestParam1", "hello")
                    .addParameter("requestParam2", "10")
                    .addParameter("requestParam3", "false")
                    .addParameter("requestParam4", "255")
                    .addParameter("requestParam5", "o")
                    .addParameter("requestParam6", "12.12")
                    .body("booooddddyyyy".getBytes())
                    .build();
            HttpResponse httpResponse = HttpResponse.getInstance();

            //when
            Method testMethod = TestClass.class.getMethod("testMethod",
                    String.class,
                    HttpResponse.class,
                    int.class,
                    boolean.class,
                    HttpRequest.class,
                    long.class,
                    String.class,
                    char.class,
                    double.class);
            Object[] resolvedParameters = argumentResolver.resolve(testMethod, httpRequest, httpResponse);

            //then
            assertThat(resolvedParameters[0]).isEqualTo("hello");
            assertThat(resolvedParameters[1]).isEqualTo(httpResponse);
            assertThat(resolvedParameters[2]).isEqualTo(10);
            assertThat(resolvedParameters[3]).isEqualTo(false);
            assertThat(resolvedParameters[4]).isEqualTo(httpRequest);
            assertThat(resolvedParameters[5]).isEqualTo(255L);
            assertThat(resolvedParameters[6]).isEqualTo("booooddddyyyy");
            assertThat(resolvedParameters[7]).isEqualTo('o');
            assertThat(resolvedParameters[8]).isEqualTo(Double.parseDouble("12.12"));
        }
    }

    @SuppressWarnings("unused")
    static class TestClass {
        public void testMethod(
                @RequestParam("requestParam1") String requestParam1,
                HttpResponse httpResponse,
                @RequestParam("requestParam2") int rp2,
                @RequestParam("requestParam3") boolean requestParam3,
                HttpRequest httpRequest,
                @RequestParam("requestParam4") long requestParam4,
                @RequestBody String body,
                @RequestParam("requestParam5") char requestParam5,
                @RequestParam("requestParam6") double requestParam6
        ) {

        }
    }
}
