package com.msg.app.config.custom;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.util.StringUtils;

public class MessageKeyGenerator implements KeyGenerator {

	// custom key generator [optional]
	@Override
	public Object generate(Object target, Method method, Object... params) {
		return String.format("%s_%s_%s", target.getClass().getSimpleName(), method.getName(),
				StringUtils.arrayToDelimitedString(params, "_"));
	}

}
