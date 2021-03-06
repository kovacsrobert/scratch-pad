package examples.chatbot.cliclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.core.JmsTemplate;

@SpringBootApplication
public class ClientApp {

	private static final Logger logger = LoggerFactory.getLogger(ClientApp.class);

	public static void main(String[] args) {
		logger.info("App is running..");
		ConfigurableApplicationContext context = SpringApplication.run(ClientApp.class, args);
		JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
		ClientMessage message = ClientMessage.clientMessage("Test mail");
		logger.info("Sending a message: {}", message.getText());
		jmsTemplate.convertAndSend("mailbox", message);
		logger.info("Sent message");
	}
}
