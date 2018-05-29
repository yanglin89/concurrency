package com.run.concurrency.example.cache;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;

/**
 * @author user
 * redis使用配置，配置了host和port，还可以通过构造函数的不同配置更多的项
 */
@Configuration
public class RedisConfig {

	@Bean(name="redisPool")
	public JedisPool jedisPool(@Value("${jedis.host}") String host,@Value("${jedis.port}") int port) {
		return new JedisPool(host,port);
	  }
}
