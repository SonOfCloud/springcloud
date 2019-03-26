package com.thoughtworks.jeremy.studentservice.listener;

import com.thoughtworks.jeremy.studentservice.entity.Student;
import com.thoughtworks.jeremy.studentservice.event.StudentCreateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StudentCreateEventListener {

    @EventListener
    public void displayStudentInfo(StudentCreateEvent studentCreateEvent){
        Student student = studentCreateEvent.getStudent();
        log.info("对象创建成功:{}", student);
    }
}
