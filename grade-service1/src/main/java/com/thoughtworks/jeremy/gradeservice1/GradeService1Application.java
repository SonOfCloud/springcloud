package com.thoughtworks.jeremy.gradeservice1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class GradeService1Application {

    public static void main(String[] args) {
        SpringApplication.run(GradeService1Application.class, args);
    }

}
