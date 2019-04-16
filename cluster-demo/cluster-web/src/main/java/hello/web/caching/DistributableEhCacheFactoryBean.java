package hello.web.caching;

import org.springframework.cache.ehcache.EhCacheFactoryBean;

public class DistributableEhCacheFactoryBean extends EhCacheFactoryBean {

	public DistributableEhCacheFactoryBean() {
		setMaxElementsOnDisk(0);
		setMaxEntriesLocalDisk(0);
		setMaxEntriesInCache(0);
	}

	@Override
	public void setMaxEntriesLocalDisk(long maxEntriesLocalDisk) {
	}
}
