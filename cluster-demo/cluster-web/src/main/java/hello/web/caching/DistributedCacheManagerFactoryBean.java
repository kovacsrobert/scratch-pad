package hello.web.caching;

import static hello.util.EnvironmentUtils.getConfiguration;
import static hello.web.config.EnvironmentVariables.TERRACOTTA_HOSTS;
import static java.util.concurrent.TimeUnit.HOURS;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.TerracottaClientConfiguration;
import net.sf.ehcache.config.TerracottaConfiguration;

public class DistributedCacheManagerFactoryBean implements FactoryBean<CacheManager>, InitializingBean, DisposableBean {

	private CacheManager cacheManager;

	public void afterPropertiesSet() throws CacheException {
		CacheConfiguration defaultCacheConfiguration = new CacheConfiguration();
		defaultCacheConfiguration.setMaxEntriesLocalHeap(100);
		defaultCacheConfiguration.setTimeToIdleSeconds(HOURS.toSeconds(4));
		defaultCacheConfiguration.setTimeToLiveSeconds(HOURS.toSeconds(24));
		defaultCacheConfiguration.addTerracotta(new TerracottaConfiguration());
		defaultCacheConfiguration.copyOnRead(true);

		Configuration configuration = new Configuration();
		configuration.setDefaultCacheConfiguration(defaultCacheConfiguration);
		String terracottaHosts = getConfiguration(TERRACOTTA_HOSTS, "cluster-terracotta-1:9510,cluster-terracotta-2:9510");
		configuration.addTerracottaConfig(new TerracottaClientConfiguration().url(terracottaHosts));
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