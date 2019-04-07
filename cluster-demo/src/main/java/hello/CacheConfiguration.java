package hello;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.bootstrap.BootstrapCacheLoader;
import net.sf.ehcache.distribution.jgroups.JGroupsBootstrapCacheLoaderFactory;
import net.sf.ehcache.distribution.jgroups.JGroupsCacheReplicator;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.Properties;

@Configuration
public class CacheConfiguration {

    @Bean
    public CacheManagerFactoryBean cacheManagerFactoryBean() {
        return new CacheManagerFactoryBean();
    }

    @Bean
    public CacheManager cacheManager() {
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
    public EhCacheFactoryBean helloCacheFactoryBean() {
        EhCacheFactoryBean ehCacheFactoryBean = new EhCacheFactoryBean();
        ehCacheFactoryBean.setCacheManager(cacheManager());
        ehCacheFactoryBean.setBootstrapCacheLoader(bootstrapCacheLoader());
        ehCacheFactoryBean.setCacheEventListeners(Collections.singleton(new JGroupsCacheReplicator(true, true, true, true)));
        ehCacheFactoryBean.setName("helloCache");
        return ehCacheFactoryBean;
    }

    @Bean
    public Ehcache helloCache() {
        return helloCacheFactoryBean().getObject();
    }
}
