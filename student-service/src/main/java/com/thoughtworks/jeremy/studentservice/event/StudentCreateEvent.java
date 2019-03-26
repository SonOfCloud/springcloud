package com.thoughtworks.jeremy.studentservice.event;

import com.thoughtworks.jeremy.studentservice.entity.Student;
import lombok.Getter;

@Getter
public class StudentCreateEvent {

    private Student student;

    public StudentCreateEvent(Student student) {
        this.student = student;
    }
}
