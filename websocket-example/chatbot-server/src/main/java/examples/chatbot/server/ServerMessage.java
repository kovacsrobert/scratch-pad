package examples.chatbot.server;

public class ServerMessage {

	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public static ServerMessage serverMessage(String text) {
		ServerMessage message = new ServerMessage();
		message.text = text;
		return message;
	}

}
