package com.example.demo.gauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.warrenstrange.googleauth.GoogleAuthenticator;

@Configuration
public class GAuthConfig {

	private final GAuthCredRepositoryAdapter gAuthCredRepositoryAdapter;

	@Autowired
	public GAuthConfig(GAuthCredRepositoryAdapter gAuthCredRepositoryAdapter) {
		this.gAuthCredRepositoryAdapter = gAuthCredRepositoryAdapter;
	}

	@Bean
	public GoogleAuthenticator googleAuthenticator() {
		GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
		googleAuthenticator.setCredentialRepository(gAuthCredRepositoryAdapter);
		return googleAuthenticator;
	}
}
