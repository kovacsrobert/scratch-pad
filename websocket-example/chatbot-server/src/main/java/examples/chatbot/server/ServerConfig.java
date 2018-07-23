package examples.chatbot.server;

import java.util.HashMap;
import java.util.Map;

import javax.jms.ConnectionFactory;

import org.apache.activemq.broker.BrokerService;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
@EnableJms
public class ServerConfig {

	@Bean
	public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
			DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		configurer.configure(factory, connectionFactory);
		return factory;
	}

	@Bean
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		converter.setTypeIdMappings(typeIdMappings());
		return converter;
	}
	
	private Map<String, Class<?>> typeIdMappings() {
		Map<String, Class<?>> typeIdMappings = new HashMap<>();
		typeIdMappings.put("examples.chatbot.cliclient.ClientMessage", ServerMessage.class);
		return typeIdMappings;
	}
	
	@Bean(initMethod = "start", destroyMethod = "stop")
	public BrokerService broker() throws Exception {
	    final BrokerService broker = new BrokerService();
	    broker.addConnector("tcp://localhost:61616");
	    broker.addConnector("vm://localhost");
	    broker.setPersistent(false);
	    return broker;
	}
}
