package webserver.myframework.handler.argument;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.myframework.bean.annotation.Component;
import webserver.myframework.handler.argument.annotation.RequestBody;
import webserver.myframework.handler.argument.annotation.RequestParam;
import webserver.myframework.model.Model;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

@Component
public class ArgumentResolverImpl implements ArgumentResolver {
    @Override
    public Object[] resolve(Method method,
                            HttpRequest httpRequest,
                            HttpResponse httpResponse,
                            Model model) throws IllegalArgumentException {
        Parameter[] parameterInfos = method.getParameters();
        List<Object> parameters = new ArrayList<>();

        for (Parameter parameterInfo : parameterInfos) {
            parameters.add(getParameter(httpRequest, httpResponse, model, parameterInfo));
        }

        return parameters.toArray();
    }

    private static Object getParameter(HttpRequest httpRequest,
                                       HttpResponse httpResponse,
                                       Model model,
                                       Parameter parameterInfo) {
        if (parameterInfo.getType().equals(HttpRequest.class)) {
            return httpRequest;
        }

        if (parameterInfo.getType().equals(HttpResponse.class)) {
            return httpResponse;
        }

        if(parameterInfo.getType().equals(Model.class)) {
            return model;
        }

        if (parameterInfo.isAnnotationPresent(RequestBody.class)) {
            return getRequestBody(httpRequest, parameterInfo);
        }

        return getTypeRequestParameter(getStringRequestParameter(httpRequest, parameterInfo), parameterInfo.getType());
    }

    private static Object getTypeRequestParameter(String parameter, Class<?> type) {
        if (type.equals(String.class)) {
            return parameter;
        } else if (type.equals(int.class)) {
            return Integer.parseInt(parameter);
        } else if (type.equals(byte.class)) {
            return Byte.parseByte(parameter);
        } else if (type.equals(short.class)) {
            return Short.parseShort(parameter);
        } else if (type.equals(long.class)) {
            return Long.parseLong(parameter);
        } else if (type.equals(float.class)) {
            return Float.parseFloat(parameter);
        } else if (type.equals(double.class)) {
            return Double.parseDouble(parameter);
        } else if (type.equals(boolean.class)) {
            return Boolean.parseBoolean(parameter);
        } else if (type.equals(char.class)) {
            if (parameter.length() != 1) {
                throw new IllegalArgumentException();
            }
            return parameter.charAt(0);
        }
        throw new IllegalArgumentException();
    }

    private static String getStringRequestParameter(HttpRequest httpRequest, Parameter parameterInfo) {
        String parameterName = getParameterName(parameterInfo);
        String parameter = httpRequest.getParameter(parameterName);
        if (parameter == null) {
            throw new IllegalArgumentException();
        }
        return parameter;
    }

    private static String getParameterName(Parameter parameterInfo) {
        if (!parameterInfo.isAnnotationPresent(RequestParam.class)) {
            throw new IllegalArgumentException();
        }
        String parameterName = parameterInfo.getAnnotation(RequestParam.class).value();
        if (parameterName.isBlank()) {
            throw new IllegalArgumentException();
        }
        return parameterName;
    }

    private static Object getRequestBody(HttpRequest httpRequest, Parameter parameterInfo) {
        Class<?> bodyType = parameterInfo.getType();
        if (!(bodyType.equals(String.class) || (bodyType.equals(byte[].class)))) {
            throw new IllegalArgumentException();
        }
        return bodyType.equals(String.class) ?
                httpRequest.getBodyToString() : httpRequest.getBody();
    }
}
