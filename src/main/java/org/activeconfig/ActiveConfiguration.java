package org.activeconfig;

import org.springframework.context.annotation.Configuration;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * ActiveConfiguration is an annotation used to mark configuration classes and specify the path to
 * the configuration file.
 */
@Configuration
@Retention(RetentionPolicy.RUNTIME)
public @interface ActiveConfiguration {
    /**
     * The path to the configuration file.
     *
     * @return the configuration file path
     */
    String value();
}
