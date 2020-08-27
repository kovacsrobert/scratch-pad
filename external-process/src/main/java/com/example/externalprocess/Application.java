package com.example.externalprocess;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws InterruptedException, IOException {
		if (args.length > 0) {
			final String prefix = args[0];
			while (!Thread.currentThread().isInterrupted()) {
				String line = String.format("%s - %s", prefix, UUID.randomUUID().toString());
				System.out.println(line);
				TimeUnit.SECONDS.sleep(1);
			}
		}

		File jar = new File("/home/rpd/workspace/scratch-pad/external-process/build/libs", "external-process-0.0.1-SNAPSHOT.jar");
		if (!jar.exists()) {
			throw new IllegalStateException("Cannot found jar file");
		}

		// SpringApplication.run(Application.class, args);
		DefaultExecutor executor = new DefaultExecutor();
		CommandLine commandLine = new CommandLine("/home/rpd/tools/jdk1.8.0_231/bin/java");
		commandLine.addArgument("-jar");
		commandLine.addArgument(jar.getAbsolutePath());
		commandLine.addArgument("external-process");
		BufferedInputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(new byte[1000]));
		executor.getStreamHandler().setProcessOutputStream(inputStream);
		executor.setWorkingDirectory(new File("/tmp"));
		ExecuteWatchdog watchDog = new ExecuteWatchdog(TimeUnit.SECONDS.toMillis(5));
		executor.setWatchdog(watchDog);
		int exitCode = executor.execute(commandLine);
		String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
		System.out.println(exitCode + " - " + result);
	}
}
