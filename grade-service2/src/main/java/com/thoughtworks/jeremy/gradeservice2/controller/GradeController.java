package com.thoughtworks.jeremy.gradeservice2.controller;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.thoughtworks.jeremy.gradeservice2.entity.Grade;
import com.thoughtworks.jeremy.gradeservice2.entity.QGrade;
import com.thoughtworks.jeremy.gradeservice2.jpa.GradeRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "班级的查询新增修改", tags = "GradeController")
@RestController
@Slf4j
public class GradeController {

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @ApiOperation(value = "查询所有的班级")
    @RequestMapping(value = {"/grades"}, method = RequestMethod.GET)
    public List<Grade> getGrades() {
        return gradeRepository.findAll();
    }

    @ApiOperation(value = "通过jpa的方式根据名称模糊查询班级列表")
    @RequestMapping(value = {"/jpa/grade/{name}"}, method = RequestMethod.GET)
    public List<Grade> getGradeByNameAndJpa(@PathVariable String name) {

        return gradeRepository.queryByNameLike(name);
    }

    @ApiOperation(value = "通过queryDsl的方式根据名称模糊查询班级列表")
    @RequestMapping(value = {"/queryDsl/grade/{name}"}, method = RequestMethod.GET)
    public List<Grade> getGradeByNameAndQueryDsl(@PathVariable String name) {
        QGrade qGrade = QGrade.grade;
        List<Grade> grades = jpaQueryFactory
                .selectFrom(qGrade)
                .where(qGrade.name.like(name))
                .fetch();
        return grades;
    }

    @ApiOperation(value = "新增班级")
    @RequestMapping(value = {"/grade"}, method = RequestMethod.POST)
    public Grade addGrade(@RequestBody Grade grade) {
        return gradeRepository.save(grade);
    }

    @ApiOperation(value = "通过班级ID获取班级信息")
    @RequestMapping(value = {"/grade/{id}"}, method = RequestMethod.GET)
    public String getGradeById(@PathVariable int id) {
        log.info("getGradeById:{}", id);
        return gradeRepository.getOne(id).getName();
    }
}
