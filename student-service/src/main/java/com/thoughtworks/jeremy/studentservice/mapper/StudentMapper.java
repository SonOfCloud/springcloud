package com.thoughtworks.jeremy.studentservice.mapper;

import com.thoughtworks.jeremy.studentservice.command.StudentCreateCommand;
import com.thoughtworks.jeremy.studentservice.dto.StudentResponseDto;
import com.thoughtworks.jeremy.studentservice.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentMapper {

    StudentMapper MAPPER = Mappers.getMapper(StudentMapper.class);

    StudentResponseDto convertToStudentResponseDto(Student student);

    Student convertToStudent(StudentCreateCommand studentCreateCommand);
}
