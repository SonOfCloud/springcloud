package com.thoughtworks.jeremy.studentservice.feign;

import org.springframework.stereotype.Component;

@Component
public class FeignGradeServiceFallBack implements FeignGradeService{
    @Override
    public String getGradeNameById(int id) {
        return "查询失败，年级信息未知";
    }
}
