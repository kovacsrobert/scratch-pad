package com.example.demo;

import javax.annotation.security.RolesAllowed;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeController {

    @GetMapping("/test")
    @RolesAllowed("ADMIN")
    public String test() {
        return "Hello, " + SecurityContextHolder.getContext().getAuthentication().getName();
    }
}