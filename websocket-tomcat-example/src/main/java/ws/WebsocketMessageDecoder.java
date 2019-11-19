package ws;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WebsocketMessageDecoder implements Decoder.Text<NotificationMessage> {

	private static final Logger logger = LogManager.getLogger(WebsocketMessageDecoder.class);

	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public NotificationMessage decode(String s) throws DecodeException {
		try {
			return objectMapper.readValue(s, NotificationMessage.class);
		} catch (JsonProcessingException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean willDecode(String s) {
		return s.contains("event") && s.contains("level") && s.contains("data");
	}

	@Override
	public void init(EndpointConfig endpointConfig) {
	}

	@Override
	public void destroy() {
	}
}
