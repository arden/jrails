package com.jrails.modules.cache.ehcache;

/**
 * ehcache公用缓存接口
 *
 * @author <a href="arden.emily@gmail.com">arden</a>
 */
public abstract class CacheManager {
    public net.sf.ehcache.Cache getCache() {
        return cache;
    }

    public void setCache(net.sf.ehcache.Cache cache) {
        this.cache = cache;
    }

    protected net.sf.ehcache.Cache cache;
}