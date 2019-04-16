package hello.web.caching;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.bootstrap.BootstrapCacheLoader;
import net.sf.ehcache.distribution.jgroups.JGroupsBootstrapCacheLoaderFactory;
import net.sf.ehcache.distribution.jgroups.JGroupsCacheReplicator;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Collections;
import java.util.Properties;

@Configuration
@Profile("cacheReplication")
public class ReplicationCachingConfiguration {

    @Bean
    public FactoryBean<CacheManager> cacheManagerFactoryBean() {
        return new ReplicationCacheManagerFactoryBean();
    }

    @Bean
    public CacheManager cacheManager() throws Exception {
        return cacheManagerFactoryBean().getObject();
    }

    @Bean
    public JGroupsBootstrapCacheLoaderFactory bootstrapCacheLoaderFactory() {
        return new JGroupsBootstrapCacheLoaderFactory();
    }

    @Bean
    public BootstrapCacheLoader bootstrapCacheLoader() {
        Properties properties = new Properties();
        properties.setProperty("bootstrapAsynchronously", "false");
        return bootstrapCacheLoaderFactory().createBootstrapCacheLoader(properties);
    }

    @Bean
    public EhCacheFactoryBean helloCacheFactoryBean() throws Exception {
        EhCacheFactoryBean ehCacheFactoryBean = new EhCacheFactoryBean();
        ehCacheFactoryBean.setCacheManager(cacheManager());
        ehCacheFactoryBean.setBootstrapCacheLoader(bootstrapCacheLoader());
        ehCacheFactoryBean.setCacheEventListeners(Collections.singleton(new JGroupsCacheReplicator(true, true, true, true)));
        ehCacheFactoryBean.setCacheName("helloCache");
        return ehCacheFactoryBean;
    }

    @Bean
    public Ehcache helloCache() throws Exception {
        return helloCacheFactoryBean().getObject();
    }
}
