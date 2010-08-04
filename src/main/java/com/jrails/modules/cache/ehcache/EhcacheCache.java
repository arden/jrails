package com.jrails.modules.cache.ehcache;

import java.util.Map;

import java.util.WeakHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.sf.ehcache.Element;
import com.jrails.modules.cache.Cache;

/**
 * 基于Ehcache实现
 *
 * @author arden
 */
public class EhcacheCache implements Cache {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private net.sf.ehcache.Cache cache;

    public void add(String key, Object value, int expiration) {
        this.safeAdd(key, value, expiration);
    }

    public boolean safeAdd(String key, Object value, int expiration) {
        if (this.cache.get(key) == null) {
            Element element = new Element(key, value);
            this.cache.put(element);
            return true;
        } else {
            return false;
        }
    }

    public void set(String key, Object value, int expiration) {
        this.add(key, value, expiration);
    }

    public boolean safeSet(String key, Object value, int expiration) {
        return this.safeAdd(key, value, expiration);
    }

    public void replace(String key, Object value, int expiration) {
        this.safeReplace(key, value, expiration);
    }

    public boolean safeReplace(String key, Object value, int expiration) {
        return this.safeAdd(key, value, expiration);
    }

    public Object get(String key) {
        Element element = this.cache.get(key);
        if (element != null) {
            return element.getObjectValue();
        }
        return null;
    }

    public Map<String, Object> get(String[] keys) {
        Map<String, Object> results = new WeakHashMap<String, Object>();
        for (String key : keys) {
            Element element = this.cache.get(key);
            if (element != null) {
                results.put(key, element.getObjectValue());
            }
        }
        return results;
    }

    public long incr(String key, int by) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public long decr(String key, int by) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void clear() {
        this.cache.removeAll();
    }

    public void delete(String key) {
        this.safeDelete(key);
    }

    public boolean safeDelete(String key) {
        return this.cache.remove(key);
    }

    public void stop() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}