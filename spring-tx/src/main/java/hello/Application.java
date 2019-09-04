package hello;

import java.util.UUID;

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
    private DtoRepository dtoRepository;

    public static void main(String[] args) {
        logger.info("App starting..");
        SpringApplication.run(Application.class, args);
        logger.info("App finishing..");
    }

    @Override
    public void run(String... args) {
        logger.info("Executing runner..");
        UUID id1 = UUID.randomUUID();

        logger.info("id1-1: " + dtoRepository.get(id1));
        dtoRepository.save(id1);
        logger.info("id1-2: " + dtoRepository.get(id1));
    }
}
