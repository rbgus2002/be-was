package webserver.myframework.bean;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import webserver.myframework.bean.annotation.Component;
import webserver.myframework.bean.exception.BeanNotFoundException;
import webserver.myframework.bean.exception.DuplicateBeanException;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("DefaultBeanContainer 테스트")
class DefaultBeanContainerTest {
    BeanContainer beanContainer;

    @BeforeEach
    void setup() {
        beanContainer = new DefaultBeanContainer();
    }

    @Nested
    @DisplayName("addBean method")
    class AddBean {
        @Nested
        @DisplayName("동일한 클래스의 bean이 이미 등록되어 있을 경우")
        class AlreadyExistSameClassBean {
            @Test
            @DisplayName("DuplicateBeanException 예외를 발생시킨다")
            void throwDuplicateBeanException() throws DuplicateBeanException {
                //given
                beanContainer.addBean(ExtendBean.class, new ExtendBean());

                //when
                //then
                assertThatThrownBy(() -> beanContainer.addBean(ExtendBean.class, new ExtendBean()))
                        .isInstanceOf(DuplicateBeanException.class);

            }
        }

        @Nested
        @DisplayName("동일한 클래스의 bean이 등록되어 있지 않은 경우")
        class SameClassBeanNotExist {
            @Test
            @DisplayName("빈을 등록한다")
            void registerBean() throws DuplicateBeanException, BeanNotFoundException {
                //given
                //when
                beanContainer.addBean(ExtendBean.class, new ExtendBean());

                //then
                Object bean = beanContainer.findBean(ExtendBean.class);
                assertThat(bean).isInstanceOf(ExtendBean.class);
            }
        }
    }

    @Nested
    @DisplayName("findBean method")
    class FindBean {
        @BeforeEach
        void setup() throws DuplicateBeanException {
            beanContainer.addBean(ExtendBean.class, new ExtendBean());
        }

        @Nested
        @DisplayName("주어진 클래스에 맞는 빈이 존재한다면")
        class isBeanExistForGivenClass {
            @Test
            @DisplayName("빈을 반환한다")
            void returnBean() throws BeanNotFoundException {
                //given
                //when
                Object bean = beanContainer.findBean(ExtendBean.class);

                //then
                assertThat(bean).isInstanceOf(ExtendBean.class);
            }
        }

        @Nested
        @DisplayName("주어진 클래스에 맞는 빈이 존재하지 않는다면")
        class isNotBeanExistForGivenClass {
            @Nested
            @DisplayName("하위 클래스에 해당하는 빈이 존재한다면")
            class isBeanExistForSuperClassOfGivenClass {
                @Test
                @DisplayName("빈을 반환한다")
                void returnBean() throws BeanNotFoundException {
                    //given
                    //when
                    Object bean = beanContainer.findBean(SuperBean.class);

                    //then
                    assertThat(bean).isInstanceOf(ExtendBean.class);
                }
            }

            @Nested
            @DisplayName("하위 클래스에 해당하는 빈이 존재하지 않는다면")
            class isNotBeanExistForSuperClassOfGivenClass {
                @Test
                @DisplayName("BeanNotFoundException 예외를 발생시킨다")
                void throwBeanNotFoundException() {
                    //given
                    //when
                    //then
                    assertThatThrownBy(() -> beanContainer.findBean(NotBean.class))
                            .isInstanceOf(BeanNotFoundException.class);
                }
            }
        }
    }

    @Nested
    @DisplayName("getBeanClassHasAnnotation method")
    class GetBeanClassHasAnnotation {
        @Test
        @DisplayName("주어진 어노테이션이 붙은 빈의 클래스들을 반환한다")
        void returnBeanHasGivenAnnotation() throws DuplicateBeanException {
            //given
            beanContainer.addBean(SuperBean.class, new SuperBean());
            beanContainer.addBean(ExtendBean.class, new ExtendBean());

            //when
            List<Class<?>> resultBeans = beanContainer.getBeanClassHasAnnotation(Component.class);

            //then
            assertThat(resultBeans.size()).isEqualTo(1);
            assertThat(resultBeans.get(0)).isEqualTo(ExtendBean.class);
        }
    }

    static class SuperBean {

    }

    @Component
    static class ExtendBean extends SuperBean {

    }

    static class NotBean {

    }
}
