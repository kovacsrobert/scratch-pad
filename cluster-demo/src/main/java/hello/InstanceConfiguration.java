package hello;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class InstanceConfiguration implements InitializingBean {

	private String instanceName;

	@Override
	public void afterPropertiesSet() {
		instanceName = System.getenv("INSTANCE_NAME");
	}

	public String getInstanceName() {
		return instanceName;
	}
}
