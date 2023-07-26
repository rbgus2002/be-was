package webserver.server;



import java.lang.reflect.Method;

public class ControllerConfig {
    //todo Object들고 있음 되겠네
    private final Class<?> clazz;
    private final Method method;

    public ControllerConfig(Class<?> clazz, Method method)  {
        this.clazz = clazz;
        this.method = method;
    }


    public Class<?> getClazz() {
        return clazz;
    }

    public Method getMethod() {
        return method;
    }
}
