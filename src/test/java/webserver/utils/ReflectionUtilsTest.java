package webserver.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import webserver.myframework.bean.annotation.Autowired;
import webserver.myframework.bean.annotation.Component;
import webserver.myframework.handler.request.annotation.Controller;
import webserver.myframework.utils.ReflectionUtils;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static webserver.myframework.utils.ReflectionUtils.*;


class ReflectionUtilsTest {
    @Nested
    @DisplayName("getClassesInPackage method")
    class GetClassesInPackage {
        @Nested
        @DisplayName("주어진 문자열의 패키지가 존재하는 경우")
        class IsPackageOfGivenStringExist {
            @Test
            @DisplayName("주어진 패키지의 모든 애플리케이션 클래스들을 가져온다")
            void getClassesInGivenPackage() throws ReflectiveOperationException, FileNotFoundException {
                //given
                //when
                List<Class<?>> allLoadedClasses = ReflectionUtils.getClassesInPackage("webserver");

                //then
                assertThat(allLoadedClasses).containsAll(
                        List.of(getClass(), ComponentClass.class, ExtendComponentClass.class, ControllerClass.class));
            }
        }

        @Nested
        @DisplayName("주어진 문자열의 패키지가 존재하지 않는 경우")
        class IsPackageOfGivenStringNotExist {
            @Test
            @DisplayName("주어진 패키지의 모든 애플리케이션 클래스들을 가져온다")
            void getClassesInGivenPackage() {
                //given
                //when
                //then
                assertThatThrownBy(() -> ReflectionUtils.getClassesInPackage("notExist"))
                        .isInstanceOf(FileNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("isClassHasAnnotation method")
    class IsClassHasAnnotation {
        @Nested
        @DisplayName("주어진 클래스가 주어진 어노테이션을 가지고 있는 경우")
        class IsGivenClassHasGivenAnnotation {
            @Test
            @DisplayName("true를 반환한다")
            void returnTrue() {
                //given
                //when
                //then
                assertThat(classHasAnnotation(ComponentClass.class, Component.class))
                        .isTrue();
                assertThat(classHasAnnotation(ExtendComponentClass.class, Component.class))
                        .isTrue();
            }
        }

        @Nested
        @DisplayName("주어진 클래스가 주어진 어노테이션을 가지고 있지 않은 경우")
        class IsGivenClassHasNotGivenAnnotation {
            @Nested
            @DisplayName("주어진 클래스가 주어진 어노테이션이 붙은 어노테이션을 가지고 있는 경우")
            class IsGivenClassHasAnnotationHasGivenAnnotation {
                @Test
                @DisplayName("true를 반환한다")
                void returnTrue() {
                    //given
                    //when
                    //then
                    assertThat(classHasAnnotation(ControllerClass.class, Component.class))
                            .isTrue();
                }
            }

            @Nested
            @DisplayName("주어진 클래스가 주어진 어노테이션이 붙은 어노테이션을 가지고 있지 않은 경우")
            class IsGivenClassHasNotAnnotationHasGivenAnnotation {
                @Test
                @DisplayName("false를 반환한다")
                void returnFalse() {
                    //given
                    //when
                    //then
                    assertThat(classHasAnnotation(getClass(), Component.class))
                            .isFalse();
                }
            }
        }
    }

    @Nested
    @DisplayName("getFieldsHaveAnnotation method")
    class GetFieldsHaveAnnotation {
        @Test
        @DisplayName("특정 어노테이션이 붙은 필드들만 가져온다")
        void getFieldsHaveAnnotation() throws NoSuchFieldException {
            //given
            Field autoWiredField = ComponentClass.class.getDeclaredField("autoWiredField");
            //when
            List<Field> fieldsHaveAnnotation = ReflectionUtils.getFieldsHaveAnnotation(ComponentClass.class, Autowired.class);

            //then
            assertThat(fieldsHaveAnnotation.size()).isEqualTo(1);
            assertThat(fieldsHaveAnnotation.get(0)).isEqualTo(autoWiredField);
        }
    }

    @Component
    @SuppressWarnings("unused")
    static class ComponentClass {
        @Autowired
        private Object autoWiredField;
        private Object notAutoWiredField;
    }

    static class ExtendComponentClass extends ComponentClass {

    }

    @Controller
    static class ControllerClass {

    }
}
