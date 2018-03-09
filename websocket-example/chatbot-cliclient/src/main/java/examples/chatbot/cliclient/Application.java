package examples.chatbot.cliclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.core.JmsTemplate;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		System.out.println("Chatbot-client app is running..");
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
		JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
        System.out.println("Sending an email message.");
        jmsTemplate.convertAndSend("mailbox", Message.message("Test mail"));
	}
}
