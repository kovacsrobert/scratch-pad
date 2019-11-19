package ws;

import static ws.NotificationMessageEventType.message;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WebsocketSchedulingExecutor implements Runnable {

	private static final Logger logger = LogManager.getLogger(WebsocketSchedulingExecutor.class);

	private static boolean initialized = false;

	public WebsocketSchedulingExecutor() {
		if (!initialized) {
			initialized = true;
			new Thread(this).start();
		}
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			WebsocketServer.getClientIds().forEach(clientId -> {
				NotificationMessage notificationMessage = new NotificationMessage();
				notificationMessage.setEvent(message);
				notificationMessage.setClientId(clientId);
				notificationMessage.setLevel("info");
				notificationMessage.setData("Sent from server with id " + UUID.randomUUID().toString());
				WebsocketServer.sendMessage(notificationMessage);
			});

			try {
				Thread.sleep(1_000L);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
				throw new RuntimeException(e);
			}
		}
	}
}
