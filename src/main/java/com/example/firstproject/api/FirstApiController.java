package com.example.firstproject.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // RestAPI용 컨트롤러! JSON을 반환!
// 일반 컨트롤러와 레스트 컨트롤러의 차이는?
public class FirstApiController {

    @GetMapping("/api/hello")
    public String hello() {
        return "hello world";
    }

}
