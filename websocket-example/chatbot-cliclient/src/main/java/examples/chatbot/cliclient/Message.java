package examples.chatbot.cliclient;

public class Message {

	private String text;

	public String getText() {
		return text;
	}

	public static Message message(String text) {
		Message message = new Message();
		message.text = text;
		return message;
	}
	
}
