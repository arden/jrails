package com.jrails.modules.cache.local;

import com.jrails.modules.cache.Cache;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * 基于jvm本地缓存
 *
 * @author arden
 */
public class LocalCache implements Cache {

    private Map<String, CachedElement> cache = new WeakHashMap<String, CachedElement>();

    public void add(String key, Object value, int expiration) {
        safeAdd(key, value, expiration);
    }

    public Object get(String key) {
        CachedElement cachedElement = cache.get(key);
        if (cachedElement != null && System.currentTimeMillis() >= cachedElement.getExpiration()) {
            cache.remove(key);
            return null;
        }
        return cachedElement == null ? null : cachedElement.getValue();
    }

    public void delete(String key) {
        safeDelete(key);
    }

    public Map<String, Object> get(String[] keys) {
        Map<String, Object> result = new WeakHashMap<String, Object>();
        for (String key : keys) {
            result.put(key, get(key));
        }
        return result;
    }

    public synchronized long incr(String key, int by) {
        CachedElement cachedElement = cache.get(key);
        if (cachedElement == null) {
            return -1;
        }
        long newValue = (Long) cachedElement.getValue() + by;
        cachedElement.setValue(newValue);
        return newValue;
    }

    public synchronized long decr(String key, int by) {
        CachedElement cachedElement = cache.get(key);
        if (cachedElement == null) {
            return -1;
        }
        long newValue = (Long) cachedElement.getValue() - by;
        cachedElement.setValue(newValue);
        return newValue;
    }

    public void replace(String key, Object value, int expiration) {
        safeReplace(key, value, expiration);
    }

    public void set(String key, Object value, int expiration) {
        safeSet(key, value, expiration);
    }

    public boolean safeAdd(String key, Object value, int expiration) {
        Object v = get(key);
        if (v == null) {
            set(key, value, expiration);
            return true;
        }
        return false;
    }

    public boolean safeDelete(String key) {
        CachedElement cachedElement = cache.get(key);
        if (cachedElement != null) {
            cache.remove(key);
            return true;
        }
        return false;
    }

    public boolean safeReplace(String key, Object value, int expiration) {
        CachedElement cachedElement = cache.get(key);
        if (cachedElement == null) {
            return false;
        }
        cachedElement.setExpiration(expiration * 1000 + System.currentTimeMillis());
        cachedElement.setValue(value);
        return true;
    }

    public boolean safeSet(String key, Object value, int expiration) {
        cache.put(key, new CachedElement(key, value, expiration * 1000 + System.currentTimeMillis()));
        return true;
    }

    public void stop() {
    }

    public void clear() {
        cache.clear();
    }
    //

    class CachedElement {

        private String key;
        private Object value;
        private Long expiration;

        public CachedElement(String key, Object value, Long expiration) {
            this.key = key;
            this.value = value;
            this.expiration = expiration;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public Long getExpiration() {
            return expiration;
        }

        public void setExpiration(Long expiration) {
            this.expiration = expiration;
        }
    }
}