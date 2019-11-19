package ws;

import java.io.Serializable;

public class NotificationMessage implements Serializable {

	private NotificationMessageEventType event;
	private String clientId;
	private String level;
	private String data;

	public NotificationMessageEventType getEvent() {
		return event;
	}

	public void setEvent(NotificationMessageEventType event) {
		this.event = event;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "NotificationMessage{" +
				"event='" + event + '\'' +
				", clientId='" + clientId + '\'' +
				", level='" + level + '\'' +
				", data='" + data + '\'' +
				'}';
	}
}
