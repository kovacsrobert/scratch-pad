package com.example.gateway;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@PropertySource({
		"classpath:/application.properties",
		"classpath:/github.properties",
		"classpath:/google.properties",
})
@RestController
@SpringBootApplication
public class Application extends WebSecurityConfigurerAdapter {

	@GetMapping("/")
	public String user(@AuthenticationPrincipal OAuth2User principal) {
		if (principal == null) {
			return "[anonymous]";
		}
		String name = principal.getAttribute("name");
		String email = principal.getAttribute("email");
		return name + " - " + email;
	}

	@GetMapping("/github")
	public void github(HttpServletResponse response) throws IOException {
		response.sendRedirect("/oauth2/authorization/github");
	}

	@GetMapping("/google")
	public void google(HttpServletResponse response) throws IOException {
		response.sendRedirect("/oauth2/authorization/google");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests(a -> a
				.antMatchers("/", "/github", "/google").permitAll()
				.anyRequest().authenticated())
			.exceptionHandling(e -> e.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
			.logout(l -> l.logoutSuccessUrl("/").permitAll())
			.csrf(c -> c.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
			.oauth2Login();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
