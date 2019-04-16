package hello.web.caching;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.config.TerracottaConfiguration;

@Configuration
@Profile("cacheDistribution")
public class DistributedCachingConfiguration {

    @Bean
    public FactoryBean<CacheManager> cacheManagerFactoryBean() {
        return new DistributedCacheManagerFactoryBean();
    }

    @Bean
    public CacheManager cacheManager() throws Exception {
        return cacheManagerFactoryBean().getObject();
    }

    @Bean
    public EhCacheFactoryBean helloCacheFactoryBean() throws Exception {
        EhCacheFactoryBean ehCacheFactoryBean = new DistributableEhCacheFactoryBean();
        ehCacheFactoryBean.setCacheManager(cacheManager());
        ehCacheFactoryBean.terracotta(new TerracottaConfiguration());
        ehCacheFactoryBean.setCacheName("helloCache");
        ehCacheFactoryBean.setMaxElementsOnDisk(0);
        return ehCacheFactoryBean;
    }

    @Bean
    public Ehcache helloCache() throws Exception {
        return helloCacheFactoryBean().getObject();
//        cacheManager().addCache("helloCache");
//        return cacheManager().getEhcache("helloCache");
    }
}
