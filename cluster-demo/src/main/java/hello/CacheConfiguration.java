package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import static java.util.concurrent.TimeUnit.SECONDS;

@Configuration
public class CacheConfiguration {

    @Autowired
    private HelloDao helloDao;

    @Bean
    public LoadingCache<String, String> helloCache() {
        return CacheBuilder.newBuilder()
                .expireAfterWrite(5, SECONDS)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        return helloDao.welcome(key);
                    }
                });
    }
}
