package com.run.concurrency.example.cache;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

//http://redis.cn/
/**
 * @author user
 * 利用redisPool，重写Redis的set和get方法
 *
 */
@Component
public class RedisClient {
	
	//注入Redispool
	@Resource(name="redisPool")
	private JedisPool JedisPool;
	
	//set方法
	public void set(String key,String value) throws Exception{
		Jedis jedis = null;
		try {
			jedis = JedisPool.getResource();
			jedis.set(key, value);
		} finally {
			if(jedis != null) {
				jedis.close();
			}
		}
	}
	
	//get方法
	public String get(String key) throws Exception{
		Jedis jedis = null;
		try {
			jedis = JedisPool.getResource();
			return jedis.get(key);
		} finally {
			if(jedis != null) {
				jedis.close();
			}
		}
	}
	

}
