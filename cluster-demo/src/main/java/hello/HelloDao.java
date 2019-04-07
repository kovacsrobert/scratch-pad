package hello;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class HelloDao {

    private static final Logger logger = LogManager.getLogger(HelloDao.class);

    private final Ehcache helloCache;

    public HelloDao(Ehcache helloCache) {
        this.helloCache = helloCache;
    }

    public String welcome(String name) {
        if (helloCache.isKeyInCache(name)) {
            logger.info("HelloDao.welcome(" + name + ") - loaded from cache");
            return String.valueOf(helloCache.get(name).getObjectValue());
        }

        logger.info("HelloDao.welcome(" + name + ") - loaded from imaginary DB");

        String welcomeMessage = "Hello, " + name;
        helloCache.put(new Element(name, welcomeMessage));

        return welcomeMessage;
    }
}
