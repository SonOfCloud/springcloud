package com.thoughtworks.jeremy.studentservice.controller;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.thoughtworks.jeremy.studentservice.command.StudentCreateCommand;
import com.thoughtworks.jeremy.studentservice.dto.StudentResponseDto;
import com.thoughtworks.jeremy.studentservice.entity.QStudent;
import com.thoughtworks.jeremy.studentservice.entity.Student;
import com.thoughtworks.jeremy.studentservice.event.StudentCreateEvent;
import com.thoughtworks.jeremy.studentservice.feign.FeignGradeService;
import com.thoughtworks.jeremy.studentservice.jpa.StudentRepository;
import com.thoughtworks.jeremy.studentservice.mapper.StudentMapper;
import com.thoughtworks.jeremy.studentservice.robbin.RobbinInvoker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(description = "学生的查询新增修改", tags = {"StudentController"})
@RestController
@Slf4j
public class StudentController {

    private final StudentRepository studentRepository;

    private final JPAQueryFactory jpaQueryFactory;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final RobbinInvoker robbinInvoker;

    private final FeignGradeService feignGradeService;

    @Autowired
    public StudentController(StudentRepository studentRepository, JPAQueryFactory jpaQueryFactory, ApplicationEventPublisher applicationEventPublisher, RobbinInvoker robbinInvoker, FeignGradeService feignGradeService) {
        this.studentRepository = studentRepository;
        this.jpaQueryFactory = jpaQueryFactory;
        this.applicationEventPublisher = applicationEventPublisher;
        this.robbinInvoker = robbinInvoker;
        this.feignGradeService = feignGradeService;
    }

    @ApiOperation(value = "通过queryDsl的方式根据名称获取学生的列表")
    @RequestMapping(path = {"queryDsl/students/{name}"}, method = RequestMethod.GET)
    public List<Student> getStudentsByNameAndQueryDsl(@PathVariable String name) {
        QStudent student = QStudent.student;
        return jpaQueryFactory
                .selectFrom(student)
                .where(student.name.eq(name))
                .fetch();
    }

    @ApiOperation(value = "通过Jpa的方式根据学生名称获取学生列表")
    @RequestMapping(path = {"/jpa/students/{name}"}, method = RequestMethod.GET)
    public List<Student> getStudentsByNameAndJpa(@PathVariable String name) {
        return studentRepository.queryByName(name);
    }

    @ApiOperation(value = "通过原生robbin的方式根据学生名称获取学生信息以及所在班级")
    @RequestMapping(value = {"/robbin/students/info/{name}"}, method = RequestMethod.GET)
    public List<StudentResponseDto> getStudentInfoByNameRobbin(@PathVariable String name) {
        List<Student> students = studentRepository.queryByName(name);
        return students.stream().peek(robbinInvoker::setClassNameByRobbin).map(StudentMapper.MAPPER::convertToStudentResponseDto).collect(Collectors.toList());
    }

    @ApiOperation(value = "通过Feign client的方式根据学生名称获取学生信息以及所在班级")
    @RequestMapping(value = {"/feign/students/info/{name}"}, method = RequestMethod.GET)
    public List<StudentResponseDto> getStudentInfoByNameFeign(@PathVariable String name) {
        log.info("getStudentInfoByNameFeign:{}", name);
        List<Student> students = studentRepository.queryByName(name);
        return students.stream().peek(this::setClassNameByFeignClient).map(StudentMapper.MAPPER::convertToStudentResponseDto).collect(Collectors.toList());
    }

    private void setClassNameByFeignClient(Student student) {
        String className = feignGradeService.getGradeNameById(student.getGradeId());
        student.setClassName(className);
    }

    @ApiOperation(value = "增加学生")
    @RequestMapping(value = {"/student"}, method = RequestMethod.POST)
    public Student addStudent(@RequestBody StudentCreateCommand studentCreateCommand) {
        Student save = studentRepository.save(StudentMapper.MAPPER.convertToStudent(studentCreateCommand));
        applicationEventPublisher.publishEvent(new StudentCreateEvent(save));
        return save;
    }

    @ApiOperation(value = "通过jpa方式分页查询")
    @RequestMapping(value = {"/jpa/students/{currentPage}/{pageSize}"}, method = RequestMethod.GET)
    public Page<Student> getStudentByPageAndJpa(@PathVariable int currentPage, @PathVariable int pageSize) {
        return studentRepository.findAll(PageRequest.of(currentPage, pageSize));
    }

    @ApiOperation(value = "通过queryDsl方式分页查询")
    @RequestMapping(value = {"/queryDsl/students/{currentPage}/{pageSize}"}, method = RequestMethod.GET)
    public List<Student> getStudentByPageAndQueryDsl(@PathVariable int currentPage, @PathVariable int pageSize) {
        QStudent student = QStudent.student;
        return jpaQueryFactory
                .selectFrom(student)
                .offset(currentPage == 0 ? 0 : currentPage * pageSize)
                .limit(pageSize)
                .fetch();
    }
}
