package hello.util;

import static hello.util.EnvironmentUtils.getConfiguration;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class InstanceConfiguration implements InitializingBean {

	@Value("${project.version}")
	private String projectName;

	private String instanceName;

	@Override
	public void afterPropertiesSet() {
		instanceName = getConfiguration("INSTANCE_NAME", projectName);
	}

	public String getInstanceName() {
		return instanceName;
	}
}
