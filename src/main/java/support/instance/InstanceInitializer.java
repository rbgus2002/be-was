package support.instance;

import support.annotation.AutoInject;
import support.annotation.Component;
import support.annotation.ComponentConstructor;
import utils.ClassListener;
import utils.InstanceNameConverter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class InstanceInitializer {

    private static DefaultInstanceManager defaultInstanceManager;

    public static DefaultInstanceManager getDefaultInstanceManager() {
        if (defaultInstanceManager == null) {
            defaultInstanceManager = DefaultInstanceManager.getDefaultInstanceManger();
        }
        return defaultInstanceManager;
    }

    public static void initializeContainer() {
        List<Class<?>> classes = ClassListener.scanClass("");

        Map<Class<?>, Integer> visited = new HashMap<>();

        List<Class<?>> filteredClasses = classes.stream()
                .filter(clazz -> !clazz.isAnnotation() && hasAnnotation(clazz, Component.class, visited))
                .collect(Collectors.toUnmodifiableList());

        visited.clear();

        filteredClasses.forEach(type -> addInstance(type, filteredClasses));
    }

    /**
     * 인스턴스를 {@link ComponentConstructor} 생성자, 혹은 유일한 생성자를 통해 생성합니다.
     * 만약 생성자가 {@link AutoInject} 파라미터를 가지고 있는 경우 해당 파리미터를 {@link InstanceManager}에서 가져와 주입한 후 생성하는 것을 시도합니다.
     *
     * @throws RuntimeException 생성에 실패할 경우 발생합니다.
     */
    private static void addInstance(Class<?> type, List<Class<?>> componentList) {
        if (!componentList.contains(type)) {
            throw new RuntimeException("인스턴스 메니저에서 관리하지 않는 인스턴스입니다.");
        }

        Constructor<?> componentConstructor = getComponentConstructor(type);
        Parameter[] parameters = componentConstructor.getParameters();
        Object[] args = Arrays.stream(parameters)
                .filter(parameter -> parameter.isAnnotationPresent(AutoInject.class))
                .map(parameter -> {
                            String name = parameter.getAnnotation(AutoInject.class).name();
                            name = "".equals(name) ? InstanceNameConverter.convert(parameter.getType().getName()) : name;
                            Object instance = getDefaultInstanceManager().getInstance(name, parameter.getType());
                            if (instance == null) {
                                addInstance(parameter.getType(), componentList);
                                instance = getDefaultInstanceManager().getInstance(name, parameter.getType());
                            }
                            return instance;
                        }
                ).toArray();

        String name = InstanceNameConverter.convert(type.getName());
        try {
            getDefaultInstanceManager().addInstance(name, componentConstructor.newInstance(args));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static Constructor<?> getComponentConstructor(Class<?> type) {
        Constructor<?>[] constructors = type.getConstructors();
        return constructors.length == 1 ? constructors[0] :
                Arrays.stream(constructors)
                        .filter(constructor -> constructor.isAnnotationPresent(ComponentConstructor.class))
                        .findAny()
                        .orElseThrow(() -> new RuntimeException("인스턴스를 생성할 생성자가 없습니다."));
    }

    private static boolean hasAnnotation(Class<?> clazz, Class<? extends Annotation> annotationClass, Map<Class<?>, Integer> visited) {
        if (visited.get(clazz) != null && visited.get(clazz) == 0) {
            return false;
        }
        visited.put(clazz, 0);

        if (clazz.isAnnotationPresent(annotationClass) || visited.get(clazz) == 1) {
            visited.compute(clazz, (k, v) -> 1);
            return true;
        }

        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation : annotations) {
            if (hasAnnotation(annotation.annotationType(), annotationClass, visited)) {
                visited.compute(clazz, (k, v) -> 1);
                return true;
            }
        }

        return false;
    }

}
