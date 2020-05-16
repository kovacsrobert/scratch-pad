package com.example.zipdemo;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ZipDemoApplicationTests {

	private static final Logger logger = LogManager.getLogger(ZipDemoApplicationTests.class);

	private Path tempDirectoryPath;
	private Path tempFile;

	@BeforeMethod
	public void beforeMethod() throws IOException {
		tempDirectoryPath = Files.createTempDirectory("zip-example");
	}

	@AfterMethod
	public void afterMethod() throws IOException {
		FileUtils.forceDelete(tempDirectoryPath.toFile());
		FileUtils.forceDelete(tempFile.toFile());
	}

	@Test
	void testUnzip() throws Exception {
		logger.info("Start test in: {}", tempDirectoryPath);

		tempFile = Files.createTempFile("zip-example", ".zip");
		try (InputStream inputStream = this.getClass().getResourceAsStream("/test.zip")) {
			Files.copy(inputStream, tempFile, REPLACE_EXISTING);
		}

		ZipUtil.recursiveUnzip(tempFile.toFile(), tempDirectoryPath.toFile());

		logger.info("Done.");
	}
}
