package ws;

import static ws.NotificationMessageEventType.subscribe;
import static ws.NotificationMessageEventType.unsubscribe;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
	private static final Map<String, Collection<Session>> interestedSessions = new HashMap<>();

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
		interestedSessions.keySet()
				.forEach(clientId -> interestedSessions.get(clientId).remove(session));
	}

	@OnMessage
	public void receiveMessage(Session session, NotificationMessage message) {
		logger.info("receiveMessage " + session.getId() + ", " + message);

		if (subscribe.equals(message.getEvent())) {
			if (!interestedSessions.containsKey(message.getClientId())) {
				interestedSessions.put(message.getClientId(), new HashSet<>());
			}
			interestedSessions.get(message.getClientId()).add(session);
		} else if (unsubscribe.equals(message.getEvent())) {
			if (interestedSessions.containsKey(message.getClientId())) {
				interestedSessions.get(message.getClientId()).remove(session);
			}
		}
	}

	@OnError
	public void error(Session session, Throwable t) {
		logger.error("error " + session.getId(), t);
	}

	public static void sendMessage(NotificationMessage message) {
		interestedSessions
				.getOrDefault(message.getClientId(), new HashSet<>())
				.forEach(session -> {
					try {
						logger.info("sendMessage " + message.getClientId() + ", " + session.getId());
						session.getBasicRemote().sendObject(message);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				});
	}

	public static Set<String> getClientIds() {
		return interestedSessions.keySet();
	}
}
