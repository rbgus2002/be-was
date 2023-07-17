package webserver.myframework.bean;


import webserver.myframework.bean.annotation.Autowired;
import webserver.myframework.bean.annotation.Component;
import webserver.myframework.bean.exception.BeanConstructorException;
import webserver.myframework.bean.exception.BeanNotFoundException;

import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.stream.Collectors;

import static webserver.myframework.utils.ReflectionUtils.*;

public class BeanInitializerImpl implements BeanInitializer {
    private final BeanContainer beanContainer;

    public BeanInitializerImpl(BeanContainer beanContainer) {
        this.beanContainer = beanContainer;
    }

    @Override
    public void initialize(String packageName) throws BeanConstructorException, ReflectiveOperationException, FileNotFoundException {
        List<Class<?>> beanClasses = getClassesInPackage(packageName).stream()
                .filter(clazz -> isClassHasAnnotation(clazz, Component.class) && !clazz.isAnnotation())
                .collect(Collectors.toList());

        List<Constructor<?>> beanConstructors = getBeanConstructors(beanClasses);
        checkBeanConstructorParameters(beanClasses, beanConstructors);
        beanConstructors.sort(Comparator.comparingInt(Constructor::getParameterCount));
        createBeans(beanContainer, beanConstructors);
    }

    private static void createBeans(BeanContainer beanContainer, List<Constructor<?>> beanConstructors) {
        List<Constructor<?>> failInitializedConstructors = new ArrayList<>();
        do {
            createBean(beanContainer, beanConstructors, failInitializedConstructors);
        } while (!failInitializedConstructors.isEmpty());
    }

    private static void createBean(BeanContainer beanContainer,
                                   List<Constructor<?>> beanConstructors,
                                   List<Constructor<?>> failInitializedConstructors) {
        for (Constructor<?> beanConstructor : beanConstructors) {
            try {
                List<Object> parameters = getBeanParameters(beanContainer, beanConstructor);
                Object bean = getBeanInstance(beanConstructor, parameters);
                beanContainer.addBean(bean.getClass(), bean);
            } catch (Exception e) {
                failInitializedConstructors.add(beanConstructor);
            }
        }
    }

    private static void checkBeanConstructorParameters(List<Class<?>> beanClasses,
                                                       List<Constructor<?>> beanConstructors) throws BeanConstructorException {
        List<Class<?>> parameterClasses = new ArrayList<>();
        beanConstructors.forEach(constructor -> parameterClasses.addAll(List.of(constructor.getParameterTypes())));
        for (Class<?> parameterClass : parameterClasses) {
            if(beanClasses.stream().noneMatch(parameterClass::isAssignableFrom)) {
                throw new BeanConstructorException();
            }
        }
    }

    private static List<Constructor<?>> getBeanConstructors(List<Class<?>> beanClasses) throws BeanConstructorException {
        List<Constructor<?>> beanConstructors = new ArrayList<>();
        for (Class<?> beanClass : beanClasses) {
            Constructor<?>[] constructors = beanClass.getDeclaredConstructors();
            beanConstructors.add(getBeanConstructor(constructors));
        }
        return beanConstructors;
    }

    private static Constructor<?> getBeanConstructor(Constructor<?>[] constructors) throws BeanConstructorException {
        if(constructors.length == 1) {
            return constructors[0];
        }

        List<Constructor<?>> constructorList = Arrays.stream(constructors)
                .filter(constructor -> constructor.isAnnotationPresent(Autowired.class))
                .collect(Collectors.toList());
        if(constructorList.size() != 1) {
            throw new BeanConstructorException();
        }
        return constructorList.get(0);
    }

    private static Object getBeanInstance(Constructor<?> constructor, List<Object> parameters) throws ReflectiveOperationException {
        constructor.setAccessible(true);
        if(parameters.size() == 0) {
            return constructor.newInstance();
        }
        return constructor.newInstance(parameters.toArray());
    }

    private static List<Object> getBeanParameters(BeanContainer beanContainer, Constructor<?> beanConstructor) throws BeanNotFoundException {
        List<Object> parameters = new ArrayList<>();
        for (Class<?> parameterClazz : beanConstructor.getParameterTypes()) {
            parameters.add(beanContainer.findBean(parameterClazz));
        }
        return parameters;
    }
}
