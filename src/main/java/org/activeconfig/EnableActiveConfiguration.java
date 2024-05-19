package org.activeconfig;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * EnableActiveConfiguration is an annotation used to enable the active configuration feature in
 * Spring applications.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(ActiveConfigRegistrar.class)
public @interface EnableActiveConfiguration {
}
