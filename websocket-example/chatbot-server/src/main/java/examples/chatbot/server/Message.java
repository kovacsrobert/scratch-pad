package examples.chatbot.server;

public class Message {

	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public static Message message(String text) {
		Message message = new Message();
		message.text = text;
		return message;
	}

}
