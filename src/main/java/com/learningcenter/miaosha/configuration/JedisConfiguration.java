package com.learningcenter.miaosha.configuration;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 描述:
 *
 * @author Jason
 * @email 285290078@qq.com
 * @create 2018-05-20 15:14
 **/
@Configuration
public class JedisConfiguration {
    @Bean
    public JedisPool jedisPool(JedisConnectionFactory jedisConnectionFactory){

        GenericObjectPoolConfig poolConfig = jedisConnectionFactory.getPoolConfig();
        String host = jedisConnectionFactory.getHostName();
        int port = jedisConnectionFactory.getPort();
        String password = jedisConnectionFactory.getPassword();
        int timeout = jedisConnectionFactory.getTimeout();
        JedisPool jedisPool =  new JedisPool(poolConfig,host,port,timeout,password);
        return jedisPool;
    }
}
