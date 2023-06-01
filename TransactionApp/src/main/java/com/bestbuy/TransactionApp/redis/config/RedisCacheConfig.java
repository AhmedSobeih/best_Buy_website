package com.bestbuy.TransactionApp.redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisCacheConfig {

	//Redis server host ip/domain name
	@Value("${spring.data.redis.host}")
	private String redisHost;

	//Redis server port
	@Value("${spring.data.redis.port}")
	private Integer redisPort;

	@Bean
	public JedisPool jedisPool() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		JedisPool jedisPool = new JedisPool(poolConfig, redisHost, redisPort);

		return jedisPool;
	}
}
