package com.msg.app.temp.config.archive;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.cache.CacheBuilder;
import com.msg.app.config.custom.MessageCacheErrorHandler;
import com.msg.app.config.custom.MessageKeyGenerator;

@ConditionalOnProperty(value = "cache.enable.default", havingValue = "true")
@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

	@Bean
	@Override
	public CacheManager cacheManager() {
		System.out.println("CacheConfig.cacheManager()");
		ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager() {
			@Override
			protected Cache createConcurrentMapCache(final String name) {
				return new ConcurrentMapCache(name, 
						CacheBuilder.newBuilder()
							.expireAfterWrite(10, TimeUnit.SECONDS)
							.maximumSize(10)
							.build()
							.asMap(), 
							false);
			}
		};
		cacheManager.setCacheNames(Arrays.asList("msgCache"));
		
		/*SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
		simpleCacheManager.setCaches(Arrays.asList(new ConcurrentMapCache("msgCache")));
		simpleCacheManager.initializeCaches();*/
		return cacheManager;
	}

	@Bean
	@Override
	public CacheErrorHandler errorHandler() {
		System.out.println("CacheConfig.errorHandler()");
		return new MessageCacheErrorHandler();
	}

	@Bean
	@Override
	public KeyGenerator keyGenerator() {
		System.out.println("CacheConfig.keyGenerator()");
		return new MessageKeyGenerator();
	}

}
