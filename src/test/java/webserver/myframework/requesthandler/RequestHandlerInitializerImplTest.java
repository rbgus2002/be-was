package webserver.myframework.requesthandler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import webserver.http.HttpMethod;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.myframework.bean.BeanContainer;
import webserver.myframework.bean.BeanInitializer;
import webserver.myframework.bean.BeanInitializerImpl;
import webserver.myframework.bean.DefaultBeanContainer;
import webserver.myframework.bean.annotation.Component;
import webserver.myframework.bean.exception.BeanConstructorException;
import webserver.myframework.bean.exception.BeanNotFoundException;
import webserver.myframework.requesthandler.annotation.Controller;
import webserver.myframework.requesthandler.annotation.RequestMapping;
import webserver.myframework.requesthandler.exception.IllegalHandlerReturnTypeException;
import webserver.myframework.requesthandler.exception.IllegalHandlerParameterTypeException;
import webserver.myframework.requesthandler.exception.RequestHandlerException;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.*;


@DisplayName("RequestHandlerInitializerImpl 테스트")
class RequestHandlerInitializerImplTest {
    BeanContainer beanContainer;
    BeanInitializer beanInitializer;
    RequestHandlerResolver requestHandlerResolver;
    RequestHandlerInitializer requestHandlerInitializer;

    @BeforeEach
    void setUp() throws ReflectiveOperationException, FileNotFoundException, BeanConstructorException {
        beanContainer = new DefaultBeanContainer();
        beanInitializer = new BeanInitializerImpl(beanContainer);
        requestHandlerResolver = new RequestHandlerResolverImpl();
        requestHandlerInitializer = new RequestHandlerInitializerImpl(beanContainer, requestHandlerResolver);

        beanInitializer.initialize("webserver.myframework.requesthandler");
    }

    @Nested
    @DisplayName("initialize method")
    class Initialize {
        @Test
        @DisplayName("빈으로부터 RequestHandler 객체를 만들고 이를 RequestHandlerResolver에 등록한다")
        void createRequestHandlerFromBeanAndRegisterItToResolver() throws BeanNotFoundException, RequestHandlerException, ReflectiveOperationException {
            //given
            //when
            requestHandlerInitializer.initialize();

            //then
            verifyRequestHandler("/class/correctMethod", ControllerClass.class);
            verifyRequestHandler("/correctMethod", ComponentClass.class);
        }
    }

    private void verifyRequestHandler(String uri, Class<?> controllerClass) throws RequestHandlerException, ReflectiveOperationException {
        RequestHandler handler = requestHandlerResolver.resolveHandler(uri, HttpMethod.POST);
        assertThat(handler).isNotNull();
        assertThat(handler).isInstanceOf(RequestHandlerImpl.class);

        Object resultController = getFieldVariable(handler, "controller");
        assertThat(resultController).isInstanceOf(controllerClass);

        Object resultMethod = getFieldVariable(handler, "method");
        Method testMethod = controllerClass.getMethod("correctMethod", HttpRequest.class, HttpResponse.class);
        assertThat(resultMethod).isEqualTo(testMethod);
    }

    @Nested
    @DisplayName("verifyHandlerCondition method")
    class VerifyHandlerCondition {
        Method verifyHandlerCondition;

        VerifyHandlerCondition() throws ReflectiveOperationException {
            verifyHandlerCondition = RequestHandlerInitializerImpl.class.
                    getDeclaredMethod("verifyHandlerCondition", Method.class);
            verifyHandlerCondition.setAccessible(true);
        }

        @Nested
        @DisplayName("메소드의 리턴 타입이 void가 아니라면")
        class MethodReturnTypeIsNotString {
            @Test
            @DisplayName("IllegalHandlerReturnTypeException 예외를 발생시킨다")
            void throwIllegalHandlerReturnTypeException() throws ReflectiveOperationException {
                //given
                Method parameterTypeError = ErrorController.class
                        .getDeclaredMethod("returnTypeError", HttpRequest.class, HttpResponse.class);

                //when
                //then
                try {
                    verifyHandlerCondition.invoke(requestHandlerInitializer, parameterTypeError);
                } catch (InvocationTargetException e) {
                    assertThat(e.getTargetException())
                            .isInstanceOf(IllegalHandlerReturnTypeException.class);
                }
            }
        }

        @Nested
        @DisplayName("메소드의 파라미터가 HttpRequest, HttpResponse가 아니라면")
        class MethodParameterIsNotOnlyHttpRequest {
            @Test
            @DisplayName("IllegalHandlerParameterTypeException 예외를 발생시킨다")
            void throwIllegalHandlerParameterTypeException() throws ReflectiveOperationException {
                //given
                Method parameterTypeError = ErrorController.class
                        .getDeclaredMethod("parameterTypeError", String.class);

                //when
                //then
                try {
                    verifyHandlerCondition.invoke(requestHandlerInitializer, parameterTypeError);
                } catch (InvocationTargetException e) {
                    assertThat(e.getTargetException())
                            .isInstanceOf(IllegalHandlerParameterTypeException.class);
                }
            }
        }

        @Nested
        @DisplayName("메소드의 리턴 타입이 void이고 메소드의 파라미터가 HttpRequest, HttpResponse라면")
        class MethodCanHandler {
            @Test
            @DisplayName("아무 일도 하지 않는다")
            void doNothing() throws ReflectiveOperationException {
                //given
                Method correctMethod = ControllerClass.class
                        .getDeclaredMethod("correctMethod", HttpRequest.class, HttpResponse.class);

                //when
                Object result = verifyHandlerCondition.invoke(requestHandlerInitializer, correctMethod);

                //then
                assertThat(result).isNull();
            }
        }
    }

    private static Object getFieldVariable(RequestHandler handler, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Field controllerField = handler.getClass().getDeclaredField(fieldName);
        controllerField.setAccessible(true);
        return controllerField.get(handler);
    }

    @SuppressWarnings("SameReturnValue")
    @Controller(value = "/class")
    static class ControllerClass {
        @SuppressWarnings("unused")
        @RequestMapping(value = "/correctMethod", method = HttpMethod.POST)
        public void correctMethod(HttpRequest httpRequest, HttpResponse httpResponse) {

        }
    }

    @Component
    @RequestMapping
    static class ComponentClass {
        @SuppressWarnings("unused")
        @RequestMapping(value = "/correctMethod", method = HttpMethod.POST)
        public void correctMethod(HttpRequest httpRequest, HttpResponse httpResponse) {

        }
    }

    @SuppressWarnings("SameReturnValue")
    static class ErrorController {
        @SuppressWarnings("unused")
        @RequestMapping(value = "/returnTypeError", method = HttpMethod.DELETE)
        public String returnTypeError(HttpRequest httpRequest, HttpResponse httpResponse) {
            return "wrong";
        }

        @SuppressWarnings("unused")
        @RequestMapping(value = "/parameterTypeError", method = HttpMethod.DELETE)
        public void parameterTypeError(String error) {
        }
    }
}
