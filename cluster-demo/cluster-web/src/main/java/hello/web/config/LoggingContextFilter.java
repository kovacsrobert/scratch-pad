package hello.web.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Component;

@Component
public class LoggingContextFilter implements Filter {

	private static final String INSTANCE_NAME = "instanceName";

	private final InstanceConfiguration instanceConfiguration;

	public LoggingContextFilter(InstanceConfiguration instanceConfiguration) {
		this.instanceConfiguration = instanceConfiguration;
	}

	@Override
	public void init(FilterConfig filterConfig) {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		ThreadContext.put(INSTANCE_NAME, instanceConfiguration.getInstanceName());
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		ThreadContext.remove(INSTANCE_NAME);
	}
}
