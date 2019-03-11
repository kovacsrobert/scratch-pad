package com.example.webdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private DemoProperties demoProperties;
    @Autowired
    private MyPrototype myPrototype;

    @GetMapping("/hello")
    public String hello() {
        return "Hello, Frank! For the " + myPrototype.getIndex() + " times.";
    }
}
