package hello.web.util;

import static java.util.Optional.ofNullable;

public class EnvironmentUtils {

	public static String getConfiguration(String key, String defaultValue) {
		return ofNullable(System.getProperty(key))
				.orElseGet(() -> ofNullable(System.getenv(key))
						.orElse(defaultValue));
	}

}
