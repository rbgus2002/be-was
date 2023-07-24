package view;

import http.HttpResponse;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class ViewResolver {

    private static final Map<String, MethodHandle> viewMethods = new HashMap<>();
    private static final Views views = new Views();

    public static void initialize() {
        Method[] methods = Views.class.getDeclaredMethods();
        for (Method method : methods) {
            if (!Modifier.isPublic(method.getModifiers())) continue;
            try {
                MethodHandle methodHandle = createMethodHandle(method);
                viewMethods.put(method.getName(), methodHandle);
            } catch (NoSuchMethodException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static MethodHandle createMethodHandle(Method method) throws NoSuchMethodException, IllegalAccessException {
        MethodType methodType;
        if (method.getParameterCount() > 0) {
            methodType = MethodType.methodType(byte[].class, method.getParameterTypes());
        } else {
            methodType = MethodType.methodType(byte[].class);
        }
        return MethodHandles.lookup()
                .findVirtual(Views.class, method.getName(), methodType)
                .bindTo(views);
    }

    public static byte[] resolve(HttpResponse httpResponse) throws Throwable {
        MethodHandle methodHandle = viewMethods.get((String) httpResponse.getViewParameter("view"));
        return (byte[]) methodHandle.invoke(httpResponse);
    }
}
