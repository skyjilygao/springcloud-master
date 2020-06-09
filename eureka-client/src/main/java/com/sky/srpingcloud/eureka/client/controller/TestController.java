package com.sky.srpingcloud.eureka.client.controller;

import com.sky.srpingcloud.eureka.client.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Scope("prototype")
@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private TestService testService;
    @GetMapping
    public String t1(){
        return testService.tt();
    }
}
