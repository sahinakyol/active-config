package org.activeconfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Field;
import java.nio.file.Paths;

/**
 * ActiveConfigAspect is a Spring component that listens for ContextRefreshedEvent and reloads
 * configuration beans dynamically from specified JSON configuration files.
 */
@Component
public class ActiveConfigAspect implements ApplicationListener<ContextRefreshedEvent> {
    private final ObjectMapper objectMapper;
    private final ApplicationContext applicationContext;

    /**
     * Constructs an ActiveConfigAspect with the specified ObjectMapper and ApplicationContext.
     *
     * @param objectMapper       the ObjectMapper used for JSON deserialization
     * @param applicationContext the Spring application context
     */
    @Autowired
    public ActiveConfigAspect(ObjectMapper objectMapper, ApplicationContext applicationContext) {
        this.objectMapper = objectMapper;
        this.applicationContext = applicationContext;
    }

    /**
     * Handles the ContextRefreshedEvent by checking for beans with ActiveConfiguration annotations
     * and setting up ConfigWatcher to reload their configurations.
     *
     * @param event the context refreshed event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        for (String beanName : applicationContext.getBeanDefinitionNames()) {
            Object bean = applicationContext.getBean(beanName);
            Class<?> beanClass = bean.getClass();
            if (beanClass.getSuperclass() != null && beanClass.getSuperclass().isAnnotationPresent(ActiveConfiguration.class)) {
                ActiveConfiguration annotation = beanClass.getSuperclass().getAnnotation(ActiveConfiguration.class);
                if (annotation != null) {
                    String configPath = annotation.value();
                    ConfigWatcher watcher = new ConfigWatcher(configPath, () -> reloadConfig(bean, configPath));
                    watcher.watch();
                    reloadConfig(bean, configPath);
                }
            }
        }
    }

    /**
     * Reloads the configuration for the specified bean from the given configuration file path.
     *
     * @param bean       the bean whose configuration is to be reloaded
     * @param configPath the path to the configuration file
     */
    void reloadConfig(Object bean, String configPath) {
        try {
            Object newConfig = objectMapper.readValue(Paths.get(configPath).toFile(), bean.getClass().getSuperclass());
            for (Field field : bean.getClass().getSuperclass().getDeclaredFields()) {
                field.setAccessible(true);
                field.set(bean, field.get(newConfig));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
