package com.example.demo;

import javax.annotation.security.RolesAllowed;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeController {

    public static final String TEST_PATH = "/test";

    @GetMapping(TEST_PATH)
    @RolesAllowed(UserRoles.ROLE_ADMIN)
    public String test() {
        return "Hello, " + SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
