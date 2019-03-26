package com.thoughtworks.jeremy.studentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDto implements Serializable {
    private String name;

    private Integer age;

    private String className;
}
