package hello;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import static java.util.concurrent.TimeUnit.HOURS;

public class CacheManagerFactoryBean implements FactoryBean<CacheManager>, InitializingBean, DisposableBean {

    private CacheManager cacheManager;

    public void afterPropertiesSet() throws CacheException {
        CacheConfiguration defaultCacheConfiguration = new CacheConfiguration();
        defaultCacheConfiguration.setMaxEntriesLocalHeap(100);
        defaultCacheConfiguration.setTimeToIdleSeconds(HOURS.toSeconds(4));
        defaultCacheConfiguration.setTimeToLiveSeconds(HOURS.toSeconds(24));

        Configuration configuration = new Configuration();
        configuration.setDefaultCacheConfiguration(defaultCacheConfiguration);
        configuration.addCacheManagerPeerProviderFactory(new CacheManagerPeerProviderConfiguration());
        configuration.setName("ReplicationCacheManager");

        this.cacheManager = new CacheManager(configuration);
    }

    public CacheManager getObject() {
        return this.cacheManager;
    }

    public Class<? extends CacheManager> getObjectType() {
        return (this.cacheManager != null ? this.cacheManager.getClass() : CacheManager.class);
    }

    public boolean isSingleton() {
        return true;
    }

    public void destroy() {
        this.cacheManager.shutdown();
    }
}
