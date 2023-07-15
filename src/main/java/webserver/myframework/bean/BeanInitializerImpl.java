package webserver.myframework.bean;


import utils.ReflectionUtils;
import webserver.myframework.bean.annotation.Autowired;
import webserver.myframework.bean.annotation.Component;
import webserver.myframework.bean.exception.BeanConstructorException;
import webserver.myframework.bean.exception.BeanNotFoundException;

import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.stream.Collectors;

public class BeanInitializerImpl implements BeanInitializer {
    @Override
    public void initialize(String packageName, BeanContainer beanContainer) throws BeanConstructorException, ReflectiveOperationException, FileNotFoundException {
        List<Class<?>> beanClasses = ReflectionUtils.getClassesInPackage(packageName).stream()
                .filter(clazz -> ReflectionUtils.isClassHasAnnotation(clazz, Component.class))
                .collect(Collectors.toList());

        // @Autowired가 붙은 모든 생성자 가져오기
        List<Constructor<?>> beanConstructors = getBeanConstructors(beanClasses);

        // 생성자의 파라미터 중 주입이 불가능한 생성자가 존재하는지 확인
        checkBeanConstructorParameters(beanConstructors);

        // 생성자 파라미터 수를 기준으로 정렬
        beanConstructors.sort(Comparator.comparingInt(Constructor::getParameterCount));

        // 생성자 주입
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

    private static void checkBeanConstructorParameters(List<Constructor<?>> beanConstructors) throws BeanConstructorException {
        List<Class<?>> parameterClasses = new ArrayList<>();
        beanConstructors.forEach(constructor -> parameterClasses.addAll(List.of(constructor.getParameterTypes())));
        for (Class<?> parameterClass : parameterClasses) {
            if(!parameterClasses.contains(parameterClass)) {
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
        if(constructorList.size() > 1) {
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
