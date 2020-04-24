package com.example.h2flyway;

import javax.annotation.PostConstruct;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FlywayMigrateService {

	@Value("${spring.datasource.url}")
	private String url;
	@Value("${spring.datasource.username}")
	private String user;
	@Value("${spring.datasource.password}")
	private String password;

	@PostConstruct
	public void init() {
		Flyway flyway = Flyway.configure()
				.dataSource(url, user, password)
				.locations("classpath:/	db")
				.load();
		flyway.migrate();
	}
}
