package webserver.myframework.handler.request;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import webserver.http.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.myframework.handler.request.annotation.Controller;
import webserver.myframework.handler.request.annotation.RequestMapping;
import webserver.myframework.handler.request.exception.CannotResolveHandlerException;
import webserver.myframework.handler.request.exception.DuplicateRequestHandlerException;
import webserver.myframework.handler.request.exception.RequestHandlerException;

import java.lang.reflect.Field;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.*;

@DisplayName("RequestHandlerResolverImpl 테스트")
class RequestHandlerResolverImplTest {
    RequestHandlerResolver requestHandlerResolver;

    @BeforeEach
    void setUp() {
        requestHandlerResolver = new RequestHandlerResolverImpl();
    }
    @Nested
    @DisplayName("registerHandler method")
    class RegisterHandler {
        @Nested
        @DisplayName("동일한 핸들러가 이미 존재할 경우")
        class IsSameHandlerAlreadyExist {
            @Test
            @DisplayName("DuplicateRequestHandlerException 예외를 발생시킨다")
            void throwDuplicateRequestHandlerException() throws NoSuchMethodException, DuplicateRequestHandlerException {
                //given
                requestHandlerResolver.registerHandler(getRequestInfo(), getHandler());

                //when
                //then
                assertThatThrownBy(() -> requestHandlerResolver.registerHandler(getRequestInfo(), getHandler()))
                        .isInstanceOf(DuplicateRequestHandlerException.class);
            }
        }

        @Nested
        @DisplayName("동일한 핸들러가 존재하지 않을 경우")
        class IsSameHandlerNotExist {
            @Test
            @DisplayName("핸들러를 등록한다")
            void registerHandler() throws ReflectiveOperationException, RequestHandlerException {
                //given
                //when
                requestHandlerResolver.registerHandler(getRequestInfo(), getHandler());

                //then
                Object handlerMap = getField(requestHandlerResolver, "handlerMap");
                assertThat(handlerMap).isInstanceOf(HashMap.class);
                assertThat(((HashMap<?, ?>)handlerMap).size()).isEqualTo(1);
            }
        }
    }

    @Nested
    @DisplayName("resolveHandler method")
    class ResolveHandler {
        @Nested
        @DisplayName("요청을 처리할 핸들러가 없는 경우")
        class IsMatchedHandlerNotExist {
            @Test
            @DisplayName("CannotResolveHandlerException 예외를 발새시킨다")
            void throwCannotResolveHandlerException() {
                //given
                //when
                //then
                assertThatThrownBy(() -> requestHandlerResolver.resolveHandler("/test", HttpMethod.DELETE))
                        .isInstanceOf(CannotResolveHandlerException.class);
            }
        }

        @Nested
        @DisplayName("요청을 처리할 핸들러가 있는 경우")
        class IsMatchedHandlerExist {
            @Test
            @DisplayName("핸들러를 반환한다")
            void returnHandler() throws DuplicateRequestHandlerException, CannotResolveHandlerException, ReflectiveOperationException {
                //given
                requestHandlerResolver.registerHandler(getRequestInfo(), getHandler());

                //when
                RequestHandler handler = requestHandlerResolver.resolveHandler("/test", HttpMethod.GET);

                //then
                assertThat(getField(handler, "controller"))
                        .isInstanceOf(TestRequestResolver.class);

                assertThat(getField(handler, "method")).isEqualTo(
                        TestRequestResolver.class.getMethod("handlerMethod", HttpRequest.class, HttpResponse.class));
            }
        }
    }

    @SuppressWarnings("SameReturnValue")
    @Controller
    static class TestRequestResolver {
        @SuppressWarnings("unused")
        @RequestMapping(value = "/test")
        public String handlerMethod(HttpRequest httpRequest, HttpResponse httpResponse) {
            return "handlerMethod";
        }
    }

    private static RequestHandlerImpl getHandler() throws NoSuchMethodException {
        TestRequestResolver controller = new TestRequestResolver();
        return new RequestHandlerImpl(controller,
                controller.getClass().getMethod("handlerMethod", HttpRequest.class, HttpResponse.class));
    }

    private static RequestInfo getRequestInfo() {
        return RequestInfo.builder()
                .uri("/test")
                .httpMethod(HttpMethod.GET)
                .build();
    }

    private static Object getField(Object instance, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field field = instance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(instance);
    }
}
