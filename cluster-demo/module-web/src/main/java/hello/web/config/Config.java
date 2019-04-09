package hello.web.config;

import static hello.web.config.EnvironmentVariables.DAO_URL;
import static hello.web.util.EnvironmentUtils.getConfiguration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

	@Bean
	public RestTemplateBuilder restTemplateBuilder() {
		return new RestTemplateBuilder()
				.rootUri(getConfiguration(DAO_URL, "http://cluster-dao:8090"));
	}

	@Bean
	public RestTemplate restTemplate() {
		return restTemplateBuilder().build();
	}
}
