package hello.web.provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

@Service
@Primary
public class CachingHelloProvider implements HelloProvider {

	private static final Logger logger = LogManager.getLogger(DaoBasedHelloProvider.class);

	private final Ehcache helloCache;
	private final DaoBasedHelloProvider daoBasedHelloProvider;

	public CachingHelloProvider(Ehcache helloCache, DaoBasedHelloProvider daoBasedHelloProvider) {
		this.helloCache = helloCache;
		this.daoBasedHelloProvider = daoBasedHelloProvider;
	}

	@Override
	public String welcome(String name) {
		if (helloCache.isKeyInCache(name)) {
			logger.info("HelloProvider.welcome(" + name + ") - loaded from cache");
			return String.valueOf(helloCache.get(name).getObjectValue());
		}

		String welcomeMessage = daoBasedHelloProvider.welcome(name);
		logger.info("HelloProvider.welcome(" + name + ") - loaded from Dao");
		helloCache.put(new Element(name, welcomeMessage));

		return welcomeMessage;
	}
}
