package com.swkim.review.api;

import org.springframework.web.bind.annotation.*;

@RestController
public class TestRequestApi {

    // Request Parameter 방식
    @GetMapping("/test/param")
    public String requestParam(
            @RequestParam("name") String name,
            @RequestParam("age") Integer age
    ) {
        return "Hello, Request Param, I am " + name + ", " + age;
    }

    // Path Variable 방식(Path를 변수처럼 사용한다.)
    @GetMapping("/test/path/{name}/{age}")
    public String requestPathVariable(
            @PathVariable("name") String name,
            @PathVariable("age") Integer age
    ) {
        return "Hello, Path Variable, I am " + name + ", " + age;
    }
}
