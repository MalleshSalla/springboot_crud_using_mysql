package com.springboot.blog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Example {

    @GetMapping("hello/{name}")
    public String helloWorld(@PathVariable String name){
        return "hello world "+name;
    }
}
