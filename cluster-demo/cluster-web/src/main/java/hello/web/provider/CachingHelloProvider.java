package hello.web.provider;

import static hello.web.config.Profiles.IGNORE_CACHING;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ehcache.Cache;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Primary
@Profile("!" + IGNORE_CACHING)
public class CachingHelloProvider implements HelloProvider {

	private static final Logger logger = LogManager.getLogger(DaoBasedHelloProvider.class);

	private final Cache<String, String> helloCache;

	private final DaoBasedHelloProvider daoBasedHelloProvider;

	public CachingHelloProvider(Cache<String, String> helloCache, DaoBasedHelloProvider daoBasedHelloProvider) {
		this.helloCache = helloCache;
		this.daoBasedHelloProvider = daoBasedHelloProvider;
	}

	@Override
	public String welcome(String name) {
		if (helloCache.containsKey(name)) {
			logger.info("HelloProvider.welcome(" + name + ") - loaded from cache");
			return helloCache.get(name);
		}

		String welcomeMessage = daoBasedHelloProvider.welcome(name);
		logger.info("HelloProvider.welcome(" + name + ") - loaded from Dao");
		helloCache.put(name, welcomeMessage);

		return welcomeMessage;
	}
}
