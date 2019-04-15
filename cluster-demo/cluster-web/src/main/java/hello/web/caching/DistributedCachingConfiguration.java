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

//    @Bean
//    public EhCacheFactoryBean helloCacheFactoryBean() throws Exception {
//        EhCacheFactoryBean ehCacheFactoryBean = new EhCacheFactoryBean();
//        ehCacheFactoryBean.setCacheManager(cacheManager());
//        ehCacheFactoryBean.terracotta(new TerracottaConfiguration());
//        ehCacheFactoryBean.setName("helloCache");
//        ehCacheFactoryBean.setMaxEntriesInCache(1000);
//        return ehCacheFactoryBean;
//    }

    @Bean
    public Ehcache helloCache() throws Exception {
        cacheManager().addCache("helloCache");
        return cacheManager().getEhcache("helloCache");
    }
}
