package com.jrails.modules.cache;

import com.jrails.commons.utils.TimeUtils;
import com.jrails.modules.cache.memcached.MemcachedCache;
import com.jrails.modules.cache.local.LocalCache;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 泡椒网缓存器
 *
 * @author arden
 */
public class JrailsCache {

    protected static final Logger logger = LoggerFactory.getLogger(JrailsCache.class);
    private static Cache cache = new LocalCache();

    /**
     * Add an element only if it doesn't exist.
     * @param key Element key
     * @param value Element value
     * @param expiration Ex: 10s, 3mn, 8h
     */
    public static void add(String key, Object value, String expiration) {
        cache.add(key, value, TimeUtils.parseDuration(expiration));
    }

    /**
     * Add an element only if it doesn't exist, and return only when
     * the element is effectivly cached.
     * @param key Element key
     * @param value Element value
     * @param expiration Ex: 10s, 3mn, 8h
     * @return If the element an eventually been cached
     */
    public static boolean safeAdd(String key, Object value, String expiration) {
        return cache.safeAdd(key, value, TimeUtils.parseDuration(expiration));
    }

    /**
     * Add an element only if it doesn't exist and store it indefinitly.
     * @param key Element key
     * @param value Element value
     */
    public static void add(String key, Object value) {
        cache.add(key, value, TimeUtils.parseDuration(null));
    }

    /**
     * Set an element.
     * @param key Element key
     * @param value Element value
     * @param expiration Ex: 10s, 3mn, 8h
     */
    public static void set(String key, Object value, String expiration) {
        cache.set(key, value, TimeUtils.parseDuration(expiration));
    }

    /**
     * Set an element and return only when the element is effectivly cached.
     * @param key Element key
     * @param value Element value
     * @param expiration Ex: 10s, 3mn, 8h
     * @return If the element an eventually been cached
     */
    public static boolean safeSet(String key, Object value, String expiration) {
        return cache.safeAdd(key, value, TimeUtils.parseDuration(expiration));
    }

    /**
     * Set an element and store it indefinitly.
     * @param key Element key
     * @param value Element value
     */
    public static void set(String key, Object value) {
        cache.set(key, value, TimeUtils.parseDuration(null));
    }

    /**
     * Replace an element only if it already exists.
     * @param key Element key
     * @param value Element value
     * @param expiration Ex: 10s, 3mn, 8h
     */
    public static void replace(String key, Object value, String expiration) {
        cache.replace(key, value, TimeUtils.parseDuration(expiration));
    }

    /**
     * Replace an element only if it already exists and return only when the
     * element is effectivly cached.
     * @param key Element key
     * @param value Element value
     * @param expiration Ex: 10s, 3mn, 8h
     * @return If the element an eventually been cached
     */
    public static boolean safeReplace(String key, Object value, String expiration) {
        return cache.safeReplace(key, value, TimeUtils.parseDuration(expiration));
    }

    /**
     * Replace an element only if it already exists and store it indefinitly.
     * @param key Element key
     * @param value Element value
     */
    public static void replace(String key, Object value) {
        cache.replace(key, value, TimeUtils.parseDuration(null));
    }

    /**
     * Increment the element value (must be a Number).
     * @param key Element key
     * @param by The incr value
     * @return The new value
     */
    public static long incr(String key, int by) {
        return cache.incr(key, by);
    }

    /**
     * Increment the element value (must be a Number) by 1.
     * @param key Element key
     * @return The new value
     */
    public static long incr(String key) {
        return cache.incr(key, 1);
    }

    /**
     * Decrement the element value (must be a Number).
     * @param key Element key
     * @param by The decr value
     * @return The new value
     */
    public static long decr(String key, int by) {
        return cache.decr(key, by);
    }

    /**
     * Decrement the element value (must be a Number) by 1.
     * @param key Element key
     * @return The new value
     */
    public static long decr(String key) {
        return cache.decr(key, 1);
    }

    /**
     * Retrieve an object.
     * @param key The element key
     * @return The element value or null
     */
    public static Object get(String key) {
        return cache.get(key);
    }

    /**
     * Bulk retrieve.
     * @param key List of keys
     * @return Map of keys & values
     */
    public static Map<String, Object> get(String... key) {
        return cache.get(key);
    }

    /**
     * Delete an element from the cache.
     * @param key The element key     *
     */
    public static void delete(String key) {
        cache.delete(key);
    }

    /**
     * Delete an element from the cache and return only when the
     * element is effectivly removed.
     * @param key The element key
     * @return If the element an eventually been deleted
     */
    public static boolean safeDelete(String key) {
        return cache.safeDelete(key);
    }

    /**
     * Clear all data from cache.
     */
    public static void clear() {
        cache.clear();
    }

    /**
     * Convenient clazz to get a value a class type;
     * @param <T> The needed type
     * @param key The element key
     * @param clazz The type class
     * @return The element value or null
     */
    public static <T> T get(String key, Class<T> clazz) {
        return (T) cache.get(key);
    }

    /**
     * Init the cache system.
     */
    public static void init() {
        boolean enabledMemcached = false;
        if (enabledMemcached) {
            try {
                cache = new MemcachedCache();
                logger.info("Connected to memcached");
            } catch (Exception e) {
                logger.error("Error while connecting to memcached");
                logger.warn("Fallback to local cache");
                cache = new LocalCache();
            }
        } else {
            cache = new LocalCache();
        }
    }

    /**
     * Stop the cache system.
     */
    public static void stop() {
        cache.stop();
    }
}