package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class DemoWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
				.withUser(User.withUsername("user").password("pass")
						.authorities(UserRoles.ROLE_USER)
						.passwordEncoder(passwordEncoder()::encode)
						//.roles(UserRoles.USER)
						.build())
				.withUser(User.withUsername("admin").password("pass")
						.authorities(UserRoles.ROLE_USER, UserRoles.ROLE_ADMIN)
						.passwordEncoder(passwordEncoder()::encode)
						//.roles(UserRoles.USER, UserRoles.ADMIN)
						.build());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/h2-console/**").permitAll()
				.antMatchers("/").permitAll()
				// .anyRequest().authenticated()
				.and()
				.formLogin()
				.and();
		http.csrf().disable();
		http.headers().frameOptions().disable();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//	@Bean
//	public BasicAuthenticationEntryPoint basicAuthenticationEntryPoint() {
//		BasicAuthenticationEntryPoint entryPoint = new BasicAuthenticationEntryPoint();
//		entryPoint.setRealmName("test-realm");
//		return entryPoint;
//	}
}