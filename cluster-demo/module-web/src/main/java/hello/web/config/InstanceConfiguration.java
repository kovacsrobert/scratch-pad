package hello.web.config;

import static hello.web.config.EnvironmentVariables.INSTANCE_NAME;
import static hello.web.util.EnvironmentUtils.getConfiguration;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class InstanceConfiguration implements InitializingBean {

	private String instanceName;

	@Override
	public void afterPropertiesSet() {
		instanceName = getConfiguration(INSTANCE_NAME, "cluster-web");
	}

	public String getInstanceName() {
		return instanceName;
	}
}
