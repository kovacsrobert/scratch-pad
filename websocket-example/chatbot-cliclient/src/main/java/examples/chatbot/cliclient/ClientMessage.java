package examples.chatbot.cliclient;

public class ClientMessage {

	private String text;

	public String getText() {
		return text;
	}

	public static ClientMessage clientMessage(String text) {
		ClientMessage message = new ClientMessage();
		message.text = text;
		return message;
	}

}
