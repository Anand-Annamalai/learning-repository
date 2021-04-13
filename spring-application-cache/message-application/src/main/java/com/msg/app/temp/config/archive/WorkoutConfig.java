package com.msg.app.temp.config.archive;

import java.util.Arrays;

import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.core.io.ClassPathResource;

//@Configuration
public class WorkoutConfig {
	
	//@Bean
	public CacheManager getCacheManager() { return getDefaultCacheManager(); }
	//public net.sf.ehcache.CacheManager ehCacheManager() { return getEhCacheManager(); }
	
	private net.sf.ehcache.CacheManager getEhCacheManager() {
		EhCacheManagerFactoryBean factoryBean = new EhCacheManagerFactoryBean();
		factoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
		factoryBean.setShared(true);
		System.out.println("EhCache initializer");
		return factoryBean.getObject();
	}
	
	private ConcurrentMapCacheManager getDefaultCacheManager() {
		System.out.println("ConcurrentMapCacheManager initializer");
		ConcurrentMapCacheManager cacheProvider = new ConcurrentMapCacheManager();
		cacheProvider.setCacheNames(Arrays.asList("msgCache"));
		//cacheManager.setAllowNullValues(false);
		return cacheProvider;
	}
	
	

}
