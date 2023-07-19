package webserver.myframework.requesthandler;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.myframework.bean.BeanContainer;
import webserver.myframework.bean.exception.BeanNotFoundException;
import webserver.myframework.requesthandler.annotation.Controller;
import webserver.myframework.requesthandler.annotation.RequestMapping;
import webserver.myframework.requesthandler.exception.IllegalHandlerParameterTypeException;
import webserver.myframework.requesthandler.exception.IllegalHandlerReturnTypeException;
import webserver.myframework.requesthandler.exception.RequestHandlerException;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

import static webserver.myframework.utils.ReflectionUtils.*;

public class RequestHandlerInitializerImpl implements RequestHandlerInitializer {
    private final BeanContainer beanContainer;
    private final RequestHandlerResolver resolver;

    public RequestHandlerInitializerImpl(BeanContainer beanContainer,
                                         RequestHandlerResolver requestHandlerResolver) {
        this.beanContainer = beanContainer;
        this.resolver = requestHandlerResolver;
    }

    @Override
    public void initialize() throws BeanNotFoundException, RequestHandlerException {
        List<Class<?>> beanClasses = beanContainer.getAllBeans().stream()
                .filter(clazz -> classHaveAnnotatedMethod(clazz, RequestMapping.class))
                .collect(Collectors.toList());
        for (Class<?> beanClass : beanClasses) {
            String baseURI = getBaseURI(beanClass);

            List<Method> methods = getMethodsHaveAnnotation(beanClass, RequestMapping.class);
            for (Method method : methods) {
                verifyHandlerCondition(method);
                RequestInfo requestInfo = getRequestInfo(baseURI, method);
                RequestHandler requestHandler = getRequestHandler(beanClass, method);
                resolver.registerHandler(requestInfo, requestHandler);
            }
        }
    }

    private static void verifyHandlerCondition(Method method)
            throws IllegalHandlerReturnTypeException, IllegalHandlerParameterTypeException {
        if(method.getReturnType() != void.class) {
            throw new IllegalHandlerReturnTypeException();
        }
        List<Class<?>> parameterTypes = List.of(method.getParameterTypes());
        if(parameterTypes.size() != 2 ||
           !parameterTypes.containsAll(List.of(HttpRequest.class, HttpResponse.class))) {
            throw new IllegalHandlerParameterTypeException();
        }
    }

    private RequestHandler getRequestHandler(Class<?> beanClass, Method method) throws BeanNotFoundException {
        Object bean = beanContainer.findBean(beanClass);
        return new RequestHandlerImpl(bean, method);
    }

    private static RequestInfo getRequestInfo(String baseURI, Method method) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        RequestInfo.Builder builder = RequestInfo.builder();
        builder.uri(baseURI + requestMapping.value());
        builder.httpMethod(requestMapping.method());
        return builder.build();
    }

    private static String getBaseURI(Class<?> beanClass) {
        RequestMapping requestMapping = beanClass.getAnnotation(RequestMapping.class);
        if(requestMapping == null) {
            return beanClass.getAnnotation(Controller.class).value();
        }
        return requestMapping.value();
    }
}
