package org.github.fleroy;

import org.github.fleroy.configure.AdditionalProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages = "practice")//(exclude = DataSourceAutoConfiguration.class)
@EnableConfigurationProperties(AdditionalProperties.class)
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
