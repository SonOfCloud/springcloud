package com.thoughtworks.jeremy.gradeservice2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class GradeService2Application {

    public static void main(String[] args) {
        SpringApplication.run(GradeService2Application.class, args);
    }

}
