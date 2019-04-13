package hello.dao.config;

import static hello.dao.util.EnvironmentUtils.getConfiguration;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class InstanceConfiguration implements InitializingBean {

	private String instanceName;

	@Override
	public void afterPropertiesSet() {
		instanceName = getConfiguration("INSTANCE_NAME", "cluster-dao");
	}

	public String getInstanceName() {
		return instanceName;
	}
}
