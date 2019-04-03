package hello;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class HelloDao {

    private static final Logger logger = LogManager.getLogger(HelloDao.class);

    public String welcome(String name) {
        logger.info("HelloDao.welcome(" + name + ")");
        return "Hello, " + name;
    }
}
