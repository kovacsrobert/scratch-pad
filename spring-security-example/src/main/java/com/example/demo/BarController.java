package com.example.demo;

import javax.annotation.security.RolesAllowed;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BarController {

    public static final String PATH = "/bar";
    
    @GetMapping(PATH)
    @RolesAllowed(UserRoles.ROLE_USER)
    public String foo() {
        return "Hello from bar controller";
    }
}