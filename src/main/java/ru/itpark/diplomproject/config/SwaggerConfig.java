package ru.itpark.diplomproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
// http://localhost:8080/swagger-ui.html
public class SwaggerConfig {
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ru.itpark"))
                .build();
    }
    //Тупо копипаста из проекта классой работы. путь:
    // /Users/Aydar/Downloads/Lection18/TaskTracker/02. Task Tracker/src/main/java/ru/itpark/tasktracker/config/SwaggerConfig.java
}
