package examples.chatbot.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.core.JmsTemplate;

@SpringBootApplication
public class Application {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
		logger.info("App is running..");
		
		JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
		Message message = Message.message("Test mail");
		logger.info("Sending a message: {}", message.getText());
		jmsTemplate.convertAndSend("mailbox", message);
		logger.info("Sent message");
	}
}
