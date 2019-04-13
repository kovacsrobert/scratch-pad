package hello.dao.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class HelloDao {

	private static final Logger logger = LogManager.getLogger(HelloDao.class);

	public String welcome(String name) {
		String welcomeMessage = "Hello, " + name;
		logger.info("welcomeMessage: " + welcomeMessage);
		return welcomeMessage;
	}
}
