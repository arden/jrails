package com.jrails.modules.cache.memcached;

import com.jrails.exceptions.ConfigurationException;
import com.jrails.modules.cache.Cache;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基于Memcached实现
 *
 * @author arden
 */
public class MemcachedCache implements Cache {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    MemcachedClient client;

    public MemcachedCache() throws IOException {
        client = new MemcachedClient(AddrUtil.getAddresses(System.getProperty("memcached.host")));
    }

    public void add(String key, Object value, int expiration) {
        client.add(key, expiration, value);
    }

    public Object get(String key) {
        Future<Object> future = client.asyncGet(key);
        try {
            return future.get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            future.cancel(false);
        }
        return null;
    }

    public void clear() {
        client.flush();
    }

    public void delete(String key) {
        client.delete(key);
    }

    public Map<String, Object> get(String[] keys) {
        Future<Map<String, Object>> future = client.asyncGetBulk(keys);
        try {
            return future.get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            future.cancel(false);
        }
        return new HashMap<String, Object>();
    }

    public long incr(String key, int by) {
        return client.incr(key, by);
    }

    public long decr(String key, int by) {
        return client.decr(key, by);
    }

    public void replace(String key, Object value, int expiration) {
        client.replace(key, expiration, value);
    }

    public boolean safeAdd(String key, Object value, int expiration) {
        Future<Boolean> future = client.add(key, expiration, value);
        try {
            return future.get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            future.cancel(false);
        }
        return false;
    }

    public boolean safeDelete(String key) {
        Future<Boolean> future = client.delete(key);
        try {
            return future.get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            future.cancel(false);
        }
        return false;
    }

    public boolean safeReplace(String key, Object value, int expiration) {
        Future<Boolean> future = client.replace(key, expiration, value);
        try {
            return future.get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            future.cancel(false);
        }
        return false;
    }

    public boolean safeSet(String key, Object value, int expiration) {
        Future<Boolean> future = client.set(key, expiration, value);
        try {
            return future.get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            future.cancel(false);
        }
        return false;
    }

    public void set(String key, Object value, int expiration) {
        client.set(key, expiration, value);
    }

    public void stop() {
        client.shutdown();
    }
}