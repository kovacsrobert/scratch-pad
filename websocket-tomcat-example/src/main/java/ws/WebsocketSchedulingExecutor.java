package ws;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WebsocketSchedulingExecutor implements Runnable {

	private static final Logger logger = LogManager.getLogger(WebsocketSchedulingExecutor.class);

	private static Executor executor;
	private static boolean initialized = false;

	public WebsocketSchedulingExecutor() {
		if (!initialized) {
			executor = Executors.newSingleThreadExecutor();
			executor.execute(this);
			initialized = true;
		}
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			NotificationMessage notificationMessage = new NotificationMessage();
			notificationMessage.setEvent("scheduled-message");
			notificationMessage.setLevel("info");
			notificationMessage.setData("Sent from server");
			WebsocketServer.sendMessage(notificationMessage);

			try {
				Thread.sleep(1_000L);
			} catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
				throw new RuntimeException(e);
			}
		}
	}
}
