package com.example.customdependencyresolver;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class CustomAutowireCandidateResolverConfiguration {

	@Bean
	@Lazy(false)
	public CustomAutowireCandidateResolverConfigurer customAutowireCandidateResolverConfigurer() {
		CustomAutowireCandidateResolverConfigurer customAutowireCandidateResolverConfigurer = new CustomAutowireCandidateResolverConfigurer();
		customAutowireCandidateResolverConfigurer.setAutowireCandidateResolver(new CustomAutowireCandidateResolver());
		return customAutowireCandidateResolverConfigurer;
	}
}
