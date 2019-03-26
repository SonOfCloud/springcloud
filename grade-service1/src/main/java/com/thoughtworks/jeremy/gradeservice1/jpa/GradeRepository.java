package com.thoughtworks.jeremy.gradeservice1.jpa;

import com.thoughtworks.jeremy.gradeservice1.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Integer>, QuerydslPredicateExecutor<Grade> {

    List<Grade> queryByNameLike(String name);
}
