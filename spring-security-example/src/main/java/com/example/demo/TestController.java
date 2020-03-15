package com.example.demo;

import javax.annotation.security.RolesAllowed;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping("/")
	public String index() {
		return "Hello from index";
	}

	@GetMapping("/bar")
	@RolesAllowed(UserRoles.ROLE_USER)
	public String bar() {
		return "Hello from bar controller";
	}

	@GetMapping("/foo/{arg}")
	@PreAuthorize("#arg == 'bar'")
	public String foo(@PathVariable("arg") String arg) {
		return "Foo me with " + arg;
	}

	@GetMapping("/admin")
	@RolesAllowed(UserRoles.ROLE_ADMIN)
	public String admin() {
		return "Hello, " + SecurityContextHolder.getContext().getAuthentication().getName();
	}
}
