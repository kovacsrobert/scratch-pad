package com.example.demo.gauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorConfig;

@Configuration
public class GAuthConfig {

	private final GAuthCredRepositoryAdapter gAuthCredRepositoryAdapter;

	@Autowired
	public GAuthConfig(GAuthCredRepositoryAdapter gAuthCredRepositoryAdapter) {
		this.gAuthCredRepositoryAdapter = gAuthCredRepositoryAdapter;
	}

	@Bean
	public GoogleAuthenticator googleAuthenticator() {
		GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator(googleAuthenticatorConfig());
		googleAuthenticator.setCredentialRepository(gAuthCredRepositoryAdapter);
		return googleAuthenticator;
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public GoogleAuthenticatorConfig googleAuthenticatorConfig() {
		return new GoogleAuthenticatorConfig();
	}
}
