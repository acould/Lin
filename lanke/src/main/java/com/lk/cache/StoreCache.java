package com.lk.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.log4j.Logger;

/**
 * 门店id缓存
 * @author lzh
 */
public class StoreCache {
    private Logger logger = Logger.getLogger(Cache.class);
    private final Map<String, Object> respository;
    private final ReadWriteLock readWriteLock;

    public StoreCache() {
        readWriteLock = new ReentrantReadWriteLock();
        respository = new HashMap<String, Object>();
    }
    
    /**
     * 获取缓存的值
     * @param key 存入缓存的key值
     * @return
     */
    public Object getObject(String key) {
        readWriteLock.readLock().lock();
        try {
        	Object cj = respository.get(key);
            if (cj != null ) {
                return cj;
            }
        } finally {
            readWriteLock.readLock().unlock();
        }
        logger.info("cache fail,key[" + key + "]");
        return null;
    }
    /**
     * 获取缓存的值
     * 
     * @param key 存入缓存的key值
     * @param cachetime 过期时间long类型，毫秒
     * @return
     */
    public Object getObject(String key, long cachetime) {
        readWriteLock.readLock().lock();
        try {
        	Object cj = respository.get(key);
            if (cj != null) {
                return cj;
            }
        } finally {
            readWriteLock.readLock().unlock();
        }
        return null;
    }
    /**
     * 获取缓存的值
     * 
     * @param key 存入缓存的key值
     * @return
     */
    public Object getNoExpiredObject(String key) {
        readWriteLock.readLock().lock();
        try {
        	Object cj = respository.get(key);
            if (cj != null) {
                return cj;
            }
        } finally {
            readWriteLock.readLock().unlock();
        }
        return null;
    }
    /**
     * 存入缓存
     * 
     * @param key 存入缓存的key值
     *  @param object Object存入缓存的值
     * @return
     */
    public void insertObject(String key, Object object) {
        readWriteLock.writeLock().lock();
        try {
            if (object != null && key != null) {
                respository.put(key,  object);
            }
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }
    /**
     * 清除缓存的某个值
     * 
     * @param key 存入缓存的key值
     * @return
     */
    public void clearObject(String key) {
        readWriteLock.writeLock().lock();
        try {
            respository.remove(key);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    /**
     * 真正缓存对象 
     *
     *
     */
    static class CacheObject {
        private final Object object;

        CacheObject(Object obj) {
            this.object = obj;
        }

        public boolean isExpired(long timeout) {
            return false;
        }

        public Object getObject() {
            return object;
        }
    }


}
