package com.thoughtworks.jeremy.studentservice.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "学生对象创建请求命令对象", description = "学生对象创建请求命令对象")
public class StudentCreateCommand implements Serializable {

    @ApiModelProperty(value = "学生姓名", name = "学生姓名", required = true)
    private String name;
    @ApiModelProperty(value = "学生年龄", name = "学生年龄", required = true)
    private Integer age;
    @ApiModelProperty(value = "学生所在班级id", name = "学生所在班级id", required = true)
    private Integer gradeId;
}
