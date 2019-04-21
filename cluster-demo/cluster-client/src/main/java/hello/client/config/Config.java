package hello.client.config;

import static hello.util.EnvironmentUtils.getConfiguration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(1);
		executor.setMaxPoolSize(1);
		executor.setThreadNamePrefix("default_task_executor_thread");
		executor.initialize();
		return executor;
	}

	@Bean
	public RestTemplate clusterWeb1RestTemplate() {
		return new RestTemplateBuilder()
				.rootUri(getConfiguration("CLUSTER_WEB_1_HOST", "http://cluster-web-1:8080"))
				.build();
	}

	@Bean
	public RestTemplate clusterWeb2RestTemplate() {
		return new RestTemplateBuilder()
				.rootUri(getConfiguration("CLUSTER_WEB_2_HOST", "http://cluster-web-2:8080"))
				.build();
	}
}
