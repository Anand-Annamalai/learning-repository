package com.msg.app.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.msg.app.config.custom.MessageEventListener;
import com.msg.app.config.custom.MessageKeyGenerator;

import net.sf.ehcache.Cache;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.CacheConfiguration.TransactionalMode;
import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

@ConditionalOnProperty(value = "cache.enable.ehcache", havingValue = "true")
@Configuration
@EnableCaching
public class EhCacheConfig {

	@Bean
	public EhCacheCacheManager msgCacheManager() {
		System.out.println("EhCache configuration initialization");
		// 1.build cache configuration
		CacheConfiguration echConfig = new CacheConfiguration()
				.name(MessageConstants.MESSAGE_CACHE_NAME)
				.eternal(false)
				.timeToIdleSeconds(3000)
				.timeToLiveSeconds(3600)
				.maxEntriesLocalHeap(1000)
				.transactionalMode(TransactionalMode.LOCAL)
				.memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LRU);
		
		// 2.create cache object
		Cache ehCache = new Cache(echConfig);
		// 2.1. registering cache event listeners for monitoring purpose
		ehCache.getCacheEventNotificationService().registerListener(cacheEventListener());
		
		// 3.add cache to cache manager
		cacheManager().getObject().addCache(ehCache);
		
		// 4.return EhCacheCacheManager
		return new EhCacheCacheManager(cacheManager().getObject());
		
	}
	
	@Bean
	public EhCacheManagerFactoryBean cacheManager() {
		return new EhCacheManagerFactoryBean();
	}
	
	@Bean
	public CacheEventListener cacheEventListener() {
		return new MessageEventListener();
	}

	@Bean
	public KeyGenerator keyGenerator() {
		System.out.println("Custom key generator initializer");
		return new MessageKeyGenerator();
	}

	
}
