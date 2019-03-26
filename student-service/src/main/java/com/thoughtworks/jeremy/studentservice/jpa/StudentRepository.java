package com.thoughtworks.jeremy.studentservice.jpa;

import com.thoughtworks.jeremy.studentservice.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer>, QuerydslPredicateExecutor<Student> {
    List<Student> queryByName(String name);
}
