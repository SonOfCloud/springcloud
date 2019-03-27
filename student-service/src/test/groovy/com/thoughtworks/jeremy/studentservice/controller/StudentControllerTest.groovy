package com.thoughtworks.jeremy.studentservice.controller

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.thoughtworks.jeremy.studentservice.dto.StudentResponseDto
import com.thoughtworks.jeremy.studentservice.entity.Student
import com.thoughtworks.jeremy.studentservice.feign.FeignGradeService
import com.thoughtworks.jeremy.studentservice.jpa.StudentRepository
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class StudentControllerTest extends Specification {

    def studentRepository = Mock(StudentRepository)

    def feignGradeService = Mock(FeignGradeService)

    def studentController = new StudentController(studentRepository, null, null, null, feignGradeService)

    def mapper = new ObjectMapper()

    def mockMvc

    def setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build()
    }

    def "get Student Info list by name via feign client invocation"() {
        given: "mock the response of db query and feign client invocation"
        studentRepository.queryByName(_) >> {
            [Student.builder().name("Jeremy").age(20).gradeId(1).build(),
             Student.builder().name("Jeremy").age(29).gradeId(2).build()]
        }
        feignGradeService.getGradeNameById(_) >>> ["一年级","二年级"]
        when:
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/feign/students/info/Jeremy").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn()
        List<StudentResponseDto> studentResponseDto = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<StudentResponseDto>>(){})
        then:
        studentResponseDto.size() == 2
        studentResponseDto.get(0).name == "Jeremy"
        studentResponseDto.get(0).age == 20
        studentResponseDto.get(0).className == "一年级"
        studentResponseDto.get(1).name == "Jeremy"
        studentResponseDto.get(1).age == 29
        studentResponseDto.get(1).className == "二年级"
    }
}
