# ActiveConfig

## Overview

ActiveConfig is a Spring-based library that facilitates dynamic configuration loading and reloading for Java applications. It leverages annotations and file watchers to keep application configurations in sync with their source files, allowing for real-time updates without requiring application restarts.

## Features

- Dynamic configuration loading using JSON files.
- Real-time configuration updates with file watchers.
- Easy integration with Spring-based applications using annotations.

## Project Structure

The project consists of the following main components:

- `ActiveConfigAspect`: A Spring aspect that listens for context refresh events and reloads configurations dynamically.
- `ActiveConfigRegistrar`: A registrar that registers the `ActiveConfigAspect` bean definition.
- `ActiveConfiguration`: An annotation to mark configuration classes.
- `ConfigWatcher`: A file watcher that monitors configuration files for changes.
- `EnableActiveConfiguration`: An annotation to enable the configuration feature in Spring applications.

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven

### Installation
```xml
<dependency>
    <groupId>org.activeconfig</groupId>
    <artifactId>active-config</artifactId>
    <version>1.0.0</version>
</dependency>
```
### Usage

#### Step 1: Enable Active Configuration

Enable ActiveConfig in your Spring application by adding the `@EnableActiveConfiguration` annotation to your main configuration class:

```java
import org.activeconfig.EnableActiveConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableActiveConfiguration
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```
#### Step 2: Annotate Configuration Classes
Annotate your configuration classes with @ActiveConfiguration to specify the path to the configuration file:
```java
import org.activeconfig.ActiveConfiguration;
import org.springframework.stereotype.Component;

@ActiveConfiguration("path/to/config.json")
@Component
public class MyConfig {
    private String someSetting;

    // getters and setters
}
```
#### Step 3: Create Configuration Files
Create JSON configuration files as specified in the @ActiveConfiguration annotation. For example:
```json
{
    "someSetting": "someValue"
}
```
### License
This project is licensed under the MIT License - see the LICENSE file for details.
