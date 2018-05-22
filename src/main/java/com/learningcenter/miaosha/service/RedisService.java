package com.learningcenter.miaosha.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 描述:
 *
 * @author Jason
 * @email 285290078@qq.com
 * @create 2018-05-20 15:38
 **/
@Service
public class RedisService {


    @Autowired
    private JedisPool jedisPool;


    /**
     * 获取key对应的value
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T get(String key, Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String resultStr = jedis.get(key);
            T result = convertString2Bean(resultStr, clazz);
            return result;
        } finally {
            return2Pool(jedis);
        }
    }

    /**
     * 检查某个key的值是否存在
     * @param key
     * @return
     */
    public boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key);
        } finally {
            return2Pool(jedis);
        }
    }

    /**
     * 设置key的值，用不过期
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public <T> boolean set(String key, T value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String result = convertBean2String(value);
            if (result == null) {
                return false;
            }
            jedis.set(key, result);
            return true;
        } finally {
            return2Pool(jedis);
        }
    }

    public long delete(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.del(key);
        } finally {
            return2Pool(jedis);
        }
    }

    /**
     * 设置key的值，并指定过期时间
     * @param key
     * @param value
     * @param expireSeconds
     * @param <T>
     * @return
     */
    public <T> boolean setEx(String key, T value,int expireSeconds) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String result = convertBean2String(value);
            if (result == null) {
                return false;
            }

            jedis.setex(key,expireSeconds, result);
            return true;
        } finally {
            return2Pool(jedis);
        }
    }

    /**
     * 原子递增
     * @param key
     * @return
     */
    public Long incr(String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.incr(key);
        } finally {
            return2Pool(jedis);
        }
    }
    /**
     * 原子递减
     * @param key
     * @return
     */
    public Long decr(String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.decr(key);
        } finally {
            return2Pool(jedis);
        }
    }


    public static <T> String convertBean2String(T value) {
        if (value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == short.class || clazz == Short.class) {
            return "" + value;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return "" + value;
        }
        if (clazz == long.class || clazz == Long.class) {
            return "" + value;
        }
        if (clazz == float.class || clazz == Float.class) {
            return "" + value;
        }
        if (clazz == double.class || clazz == Double.class) {
            return "" + value;
        }
        if (clazz == boolean.class || clazz == Boolean.class) {
            return "" + value;
        }
        if (clazz == byte.class || clazz == Byte.class) {
            return "" + value;
        }

        if (clazz == String.class) {
            return (String) value;
        }
        return JSONObject.toJSONString(value);
    }

    public static <T> T convertString2Bean(String result, Class<T> clazz) {
        if (result == null || result.trim().length() == 0) {
            return null;
        }

        if (clazz == short.class || clazz == Short.class) {
            return (T) Short.valueOf(result);
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(result);
        }
        if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(result);
        }
        if (clazz == float.class || clazz == Float.class) {
            return (T) Float.valueOf(result);
        }
        if (clazz == double.class || clazz == Double.class) {
            return (T) Double.valueOf(result);
        }
        if (clazz == boolean.class || clazz == Boolean.class) {
            return (T) Boolean.valueOf(result);
        }
        if (clazz == byte.class || clazz == Byte.class) {
            return (T) Byte.valueOf(result);
        }

        if (clazz == String.class) {
            return (T) result;
        }
        return JSONObject.parseObject(result, clazz);
    }

    private void return2Pool(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }

    }
}
