package webserver.myframework.bean;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import webserver.myframework.bean.annotation.Autowired;
import webserver.myframework.bean.annotation.Component;
import webserver.myframework.bean.exception.BeanConstructorException;

import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("BeanInitializerImpl 테스트")
class BeanInitializerImplTest {
    BeanContainer beanContainer;
    BeanInitializer beanInitializer;

    @BeforeEach
    void setUp() {
        beanContainer = new DefaultBeanContainer();
        beanInitializer = new BeanInitializerImpl(beanContainer);
    }

    @Nested
    @DisplayName("checkBeanConstructorParameters method")
    class CheckBeanConstructorParameters {
        @Nested
        @DisplayName("생성자 주입을 통해 빈이 아닌 객체를 주입받으려 하는 경우")
        class wantToInjectedNotBean {
            @Test
            @DisplayName("BeanConstructorException 예외를 발생시킨다")
            void throwBeanInitializeException() throws ReflectiveOperationException, FileNotFoundException, BeanConstructorException {
                //given
                beanInitializer.initialize("webserver.myframework.bean");

                //when
                //then
                Method checkBeanConstructorParametersMethod = beanInitializer.getClass()
                        .getDeclaredMethod("checkBeanConstructorParameters", List.class, List.class);
                checkBeanConstructorParametersMethod.setAccessible(true);
                Constructor<?>[] constructors = ErrorBean.class.getConstructors();

                try {
                    checkBeanConstructorParametersMethod.invoke(beanInitializer, List.of(ErrorBean.class), List.of(constructors));
                } catch (InvocationTargetException e) {
                    Throwable targetException = e.getTargetException();
                    assertThat(targetException).isInstanceOf(BeanConstructorException.class);
                }
            }
        }
    }

    @Nested
    @DisplayName("initialize method")
    class Initialize {
        @Test
        @DisplayName("주어진 패키지로부터 빈을 탐색하여 초기화한다")
        void findBeansInGivenPackageAndInitializeThey() throws Exception {
            //given
            //when
            beanInitializer.initialize("webserver.myframework.bean");

            //then
            Object bean1 = beanContainer.findBean(NotInjectedBean.class);
            assertThat(bean1)
                    .isNotNull()
                    .isInstanceOf(NotInjectedBean.class);

            Object bean2 = beanContainer.findBean(InjectedBean.class);
            assertThat(bean2)
                    .isNotNull()
                    .isInstanceOf(InjectedBean.class);

            NotInjectedBean injectedBean = ((InjectedBean) bean2).getNotInjectedBean();
            assertThat(injectedBean).isNotNull()
                    .isInstanceOf(NotInjectedBean.class);
        }
    }

    @Component
    static class NotInjectedBean {

    }

    @Component
    static class InjectedBean {
        @SuppressWarnings("FieldCanBeLocal")
        private final NotInjectedBean notInjectedBean;

        @Autowired
        InjectedBean(NotInjectedBean notInjectedBean) {
            this.notInjectedBean = notInjectedBean;
        }

        public NotInjectedBean getNotInjectedBean() {
            return notInjectedBean;
        }
    }

    static class ErrorBean {
        @SuppressWarnings("FieldCanBeLocal")
        private final String errorParameter;

        public ErrorBean(String errorParameter) {
            this.errorParameter = errorParameter;
        }
    }
}
