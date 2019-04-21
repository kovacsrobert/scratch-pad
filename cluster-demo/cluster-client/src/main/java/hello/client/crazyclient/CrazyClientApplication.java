package hello.client.crazyclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CrazyClientApplication implements CommandLineRunner {

	@Autowired
	private RestTemplate clusterWeb1RestTemplate;
	@Autowired
	private RestTemplate clusterWeb2RestTemplate;
	@Autowired
	private CrazyClientParameterProvider parameterProvider;
	@Autowired
	private TaskExecutor taskExecutor;

	@Override
	public void run(String... args) {
		CrazyClientThread crazyClientThread1 = new CrazyClientThread(clusterWeb1RestTemplate, "/hello/%s", parameterProvider);
		CrazyClientThread crazyClientThread2 = new CrazyClientThread(clusterWeb2RestTemplate, "/hello/%s", parameterProvider);

		taskExecutor.execute(crazyClientThread1);
		taskExecutor.execute(crazyClientThread2);
	}
}
