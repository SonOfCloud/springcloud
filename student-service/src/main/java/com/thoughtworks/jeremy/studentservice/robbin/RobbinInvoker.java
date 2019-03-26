package com.thoughtworks.jeremy.studentservice.robbin;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.thoughtworks.jeremy.studentservice.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RobbinInvoker {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand
    public void setClassNameByRobbin(Student student) {
        String className = restTemplate.getForEntity("http://grade-service/grade/" + student.getGradeId(), String.class).getBody();
        student.setClassName(className);
    }
}
