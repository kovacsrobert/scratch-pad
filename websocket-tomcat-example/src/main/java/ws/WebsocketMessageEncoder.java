package ws;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WebsocketMessageEncoder implements Encoder.Text<NotificationMessage> {

	private static final Logger logger = LogManager.getLogger(WebsocketMessageEncoder.class);

	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String encode(NotificationMessage notificationMessage) {
		try {
			return objectMapper.writeValueAsString(notificationMessage);
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void init(EndpointConfig endpointConfig) {
	}

	@Override
	public void destroy() {
	}
}
