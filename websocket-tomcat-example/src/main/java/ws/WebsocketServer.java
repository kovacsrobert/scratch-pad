package ws;

import java.util.Collection;
import java.util.HashSet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ServerEndpoint(
		value = "/ws-test",
		encoders = { WebsocketMessageEncoder.class },
		decoders = { WebsocketMessageDecoder.class })
public class WebsocketServer {

	private static final Logger logger = LogManager.getLogger(WebsocketServer.class);

	private static final Collection<Session> sessions = new HashSet<>();

	public WebsocketServer() {
		logger.info("WebsocketServer()");
		new WebsocketSchedulingExecutor();
	}

	@OnOpen
	public void clientConnected(Session session) {
		logger.info("clientConnected " + session.getId());
		sessions.add(session);
	}

	@OnClose
	public void clientDisconnected(Session session) {
		logger.info("clientDisconnected " + session.getId());
		sessions.remove(session);
	}

	@OnMessage
	public void receiveMessage(Session session, NotificationMessage message) {
		logger.info("receiveMessage " + session.getId() + ", " + message);
	}

	@OnError
	public void error(Session session, Throwable t) {
		logger.error("error " + session.getId(), t);
	}

	public static void sendMessage(NotificationMessage message) {
		logger.info("sendMessage " + message);
		sessions.forEach(session -> {
			try {
				session.getBasicRemote().sendObject(message);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		});
	}
}
