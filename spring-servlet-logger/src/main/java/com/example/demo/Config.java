package com.example.demo;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

	@Bean
	public FilterRegistrationBean someFilterRegistration() {
	    FilterRegistrationBean registration = new FilterRegistrationBean();
	    registration.setFilter(payloadLoggingFilter());
	    registration.addUrlPatterns("/get");
	    return registration;
	}
	
	@Bean
	public PayloadLoggingFilter payloadLoggingFilter() {
		return new PayloadLoggingFilter();
	}	
}
