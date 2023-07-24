package webserver.myframework.handler.request;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.myframework.bean.BeanContainer;
import webserver.myframework.bean.exception.BeanNotFoundException;
import webserver.myframework.handler.argument.ArgumentResolver;
import webserver.myframework.handler.request.annotation.Controller;
import webserver.myframework.handler.request.annotation.RequestMapping;
import webserver.myframework.handler.request.exception.IllegalHandlerParameterTypeException;
import webserver.myframework.handler.request.exception.IllegalHandlerReturnTypeException;
import webserver.myframework.handler.request.exception.RequestHandlerException;

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
            throws IllegalHandlerReturnTypeException {
        if(method.getReturnType() != void.class) {
            throw new IllegalHandlerReturnTypeException();
        }
    }

    private RequestHandler getRequestHandler(Class<?> beanClass, Method method) throws BeanNotFoundException {
        Object bean = beanContainer.findBean(beanClass);
        Object argumentResolver = beanContainer.findBean(ArgumentResolver.class);
        return new RequestHandlerImpl(bean, method, (ArgumentResolver) argumentResolver);
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
