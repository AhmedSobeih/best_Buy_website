package com.bestbuy.TransactionApp.redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisCacheConfig {

	//Redis server host ip/domain name
	@Value("${redis.host}")
	private String redisHost;

	//Redis server port
	@Value("${redis.port}")
	private Integer redisPort;

	//Timeout value in seconds
	@Value("${redis.timeout}")
	private Integer redisTimeout;
	
	// Maximum Active Connection Count
	@Value("${redis.maximumActiveConnectionCount}")
	private Integer redisMaximumActiveConnectionCount;

	@Bean
	public JedisPool jedisPool() {

		// Initialize JedisPoolConfig object
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		// Set maximum active connection count
		poolConfig.setMaxTotal(redisMaximumActiveConnectionCount);
		//Initialize JedisPool object using server related properties.
		//If you are using additional properties like,{ password ,ssl.enabled etc } you have to pass those 
		// values into JedisPool constructor.
		JedisPool jedisPool = new JedisPool(poolConfig, redisHost, redisPort, redisTimeout);

		return jedisPool;
	}
}
