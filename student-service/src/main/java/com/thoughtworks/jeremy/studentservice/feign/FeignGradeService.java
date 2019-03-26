package com.thoughtworks.jeremy.studentservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "grade-service", serviceId = "grade-service", fallback = FeignGradeServiceFallBack.class)
public interface FeignGradeService {

    @GetMapping(value = "/grade/{id}")
    String getGradeNameById(@PathVariable int id);
}
