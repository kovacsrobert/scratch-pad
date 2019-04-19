package hello.web.caching;

import static hello.util.EnvironmentUtils.getConfiguration;
import static hello.web.config.EnvironmentVariables.TERRACOTTA_HOST;
import static hello.web.config.Profiles.IGNORE_CACHING;
import static org.ehcache.clustered.client.config.builders.ClusteredResourcePoolBuilder.clusteredDedicated;
import static org.ehcache.config.builders.CacheManagerBuilder.newCacheManagerBuilder;
import static org.ehcache.config.builders.ResourcePoolsBuilder.newResourcePoolsBuilder;
import static org.ehcache.config.units.EntryUnit.ENTRIES;
import static org.ehcache.config.units.MemoryUnit.MB;

import java.net.URI;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.clustered.client.config.builders.ClusteringServiceConfigurationBuilder;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.ResourcePools;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!" + IGNORE_CACHING)
public class CachingConfig {

	@Bean
	public ResourcePools helloCacheResourcePools() {
		return newResourcePoolsBuilder()
				.heap(1L, ENTRIES)
				.offheap(1L, MB)
				.with(clusteredDedicated("offheap-hello", 2, MB))
				.build();
	}

	@Bean
	public CacheConfiguration<String, String> helloCacheConfiguration() {
		return CacheConfigurationBuilder
				.newCacheConfigurationBuilder(String.class, String.class, helloCacheResourcePools())
				.build();
	}

	@Bean
	public CacheManager helloCacheManager() {
		String terracottaHost = getConfiguration(TERRACOTTA_HOST, "terracotta://cluster-terracotta:9510");
		return newCacheManagerBuilder()
				.withCache("helloCache", helloCacheConfiguration())
				.with(ClusteringServiceConfigurationBuilder.cluster(URI.create(terracottaHost)).autoCreate())
				.build(true);
	}

	@Bean
	public Cache<String, String> helloCache() {
		return helloCacheManager().getCache("helloCache", String.class, String.class);
	}
}
