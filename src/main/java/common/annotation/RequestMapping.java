package common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import webserver.http.Http;
import webserver.http.Http.Method;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface RequestMapping {
    String value() default "";

    Http.Method method() default Method.GET;
}
