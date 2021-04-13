package com.msg.app.config.custom;

import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

public class MessageCacheErrorHandler implements CacheErrorHandler {

	@Override
	public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
		System.out.println("*****handleCacheGet*****");
		System.out.println(cache.getName());
		System.out.println(key);
		System.out.println(exception);
		
	}

	@Override
	public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
		System.out.println("*****handleCachePut*****");
		System.out.println(cache.getName());
		System.out.println(key);
		System.out.println(value);
		System.out.println(exception);
		
	}

	@Override
	public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
		System.out.println("*****handleCacheEvict*****");
		System.out.println(cache.getName());
		System.out.println(key);
		System.out.println(exception);
		
	}

	@Override
	public void handleCacheClearError(RuntimeException exception, Cache cache) {
		System.out.println("*****handleCacheClear*****");
		System.out.println(cache.getName());
		System.out.println(exception);
	}

}
