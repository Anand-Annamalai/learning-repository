package com.msg.app.config.custom;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;

public class MessageEventListener implements CacheEventListener {

	@Override
	public void notifyElementPut(Ehcache cache, Element element) throws CacheException {
		System.out.println(String.format("%s_%s_%s_%s", "Triggered Add element", cache.getName(),
				element.getObjectKey(), element.getObjectValue()));
	}

	@Override
	public void notifyElementUpdated(Ehcache cache, Element element) throws CacheException {
		System.out.println(
				String.format("%s_%s_%s", "Triggered Update element", cache.getName(), element.getObjectValue()));
	}

	@Override
	public void notifyElementEvicted(Ehcache cache, Element element) {
		System.out.println(
				String.format("%s_%s_%s", "Triggered Remove element", cache.getName(), element.getObjectValue()));
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public void notifyElementExpired(Ehcache cache, Element element) {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyRemoveAll(Ehcache cache) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyElementRemoved(Ehcache cache, Element element) throws CacheException {
		// TODO Auto-generated method stub

	}

}
