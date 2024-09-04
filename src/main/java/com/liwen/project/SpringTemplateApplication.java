package com.liwen.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@EnableOpenApi
public class SpringTemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringTemplateApplication.class, args);
    }

}
