package hello.client.crazyclient;

import java.util.Optional;
import java.util.function.Supplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class CrazyClientThread extends Thread {

	private static final Logger logger = LogManager.getLogger(CrazyClientThread.class);

	private final RestTemplate restTemplate;
	private final String urlPattern;
	private final Supplier<Object[]> parameterSupplier;

	public CrazyClientThread(RestTemplate restTemplate, String urlPattern, Supplier<Object[]> parameterSupplier) {
		this.restTemplate = restTemplate;
		this.urlPattern = urlPattern;
		this.parameterSupplier = parameterSupplier;
	}

	@Override
	public void run() {
		while (!this.isInterrupted()) {
			sleepSome();
			Object[] parameter = parameterSupplier.get();
			Optional<String> welcomeMessage = getWelcomeMessage(parameter);
			if (welcomeMessage.isPresent()) {
				logger.info("Client parameters: " + parameter + ", welcome message: " + welcomeMessage);
			} else {
				logger.info("Client parameters: " + parameter + "did not get response");
			}

		}
	}

	private void sleepSome() {
		try {
			sleep(1000);
		} catch (InterruptedException e) {
			// left blank
		}
	}

	private Optional<String> getWelcomeMessage(Object[] parameter) {
		try {
			String url = String.format(urlPattern, parameter);
			return Optional.ofNullable(restTemplate.getForEntity(url, String.class)).map(ResponseEntity::getBody);
		}  catch (Exception e) {
			logger.error("Failed to fetch welcome message", e);
		}
		return Optional.empty();
	}
}
