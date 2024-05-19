package org.activeconfig;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * ActiveConfigRegistrar is a registrar that registers the ActiveConfigAspect bean definition.
 */
public class ActiveConfigRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition(ActiveConfigAspect.class);
        registry.registerBeanDefinition("activeConfigAspect", beanDefinition);
    }
}
