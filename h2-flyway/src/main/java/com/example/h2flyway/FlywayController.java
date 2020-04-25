package com.example.h2flyway;

import javax.annotation.PostConstruct;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flyway")
public class FlywayController {

	@Value("${spring.datasource.url}")
	private String url;
	@Value("${spring.datasource.username}")
	private String user;
	@Value("${spring.datasource.password}")
	private String password;

	private Flyway flyway;

	@PostConstruct
	public void init() {
		flyway = Flyway.configure()
				.dataSource(url, user, password)
				//.locations("classpath:/db")
				.load();
	}

	@GetMapping("/info")
	public String info() {
		MigrationInfoService info = flyway.info();
		return "Info: " + info.toString();
	}

	@GetMapping("/migrate")
	public String migrate() {
		int appliedMigrations = flyway.migrate();
		return "Migrated: " + appliedMigrations;
	}

	@GetMapping("/undo")
	public String undo() {
		int appliedMigrations = flyway.undo();
		return "Undone: " + appliedMigrations;
	}
}
