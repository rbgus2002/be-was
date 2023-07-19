package support.annotation;

import webserver.response.HttpStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 컨트롤러 처리 이후 반환할 http Response Status를 정의한다.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ResponseStatus {

    HttpStatus status() default HttpStatus.OK;

    /**
     * Redirection이 가능한 {@link HttpStatus}의 경우 해당 필드를 사용한다. <br />
     * 만약 불필요할 경우 해당 값은 무시된다.
     */
    String redirectionUrl() default "";

}
