package support.annotation;


import java.lang.annotation.*;

/**
 * {@link support.DefaultInstanceManager}의 관리 대상이 되는 클래스에 사용합니다. <br />
 * Note: Singleton Instance로 관리됩니다.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
public @interface Container {
}