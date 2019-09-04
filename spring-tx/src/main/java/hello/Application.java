package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application implements CommandLineRunner {

	private static Logger logger = LoggerFactory.getLogger(Application.class);

	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private SimpleTestApp simpleTestApp;
	@Autowired
	private ConcurrentTestApp concurrentTestApp;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
//		SpringApplication.run(ConcurrentTestApp.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("App starting..");

		simpleTestApp.run();

		logger.info("App finishing..");
		SpringApplication.exit(applicationContext);
	}
}
