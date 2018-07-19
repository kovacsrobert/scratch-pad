package examples.chatbot.server;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

	@JmsListener(destination= "mailbox")
	public void receiveMessage(Message message) {
		System.out.println("Receiver got message: " + message.getText());
	}
	
}
