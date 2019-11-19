package ws;

import java.io.Serializable;

public class NotificationMessage implements Serializable {

	private String event;
	private String level;
	private String data;

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
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
				", level='" + level + '\'' +
				", data='" + data + '\'' +
				'}';
	}
}
