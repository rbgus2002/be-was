package annotation;

import view.renderer.HtmlRenderer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RendererMapping {
    Class<? extends HtmlRenderer> name();
}
