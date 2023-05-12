package com.bestbuy.TransactionApp.service.Redis;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bestbuy.TransactionApp.model.ShoppingCart;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisCacheService{

	@Autowired
	private JedisPool jedisPool;
    private final Logger logger = LoggerFactory.getLogger(RedisCacheService.class);


	private final Gson gson = new GsonBuilder()
	.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
	.create();

	//TTL(Time to live) of session data in seconds 
	@Value("${redis.sessiondata.ttl}")
	private int sessiondataTTL;

	// Acquire Jedis instance from the jedis pool.
	private Jedis acquireJedisInstance() {

		return jedisPool.getResource();
	}

	// Releasing the current Jedis instance once completed the job.
	private void releaseJedisInstance(Jedis jedis) {

		if (jedis != null) {
			jedis.close();
			jedis = null;
		}
	}

	public ShoppingCart storeShoppingCart(Long userId, ShoppingCart shoppingCart) {

		Jedis jedis = null;
        String userId_str = "Shopping_cart_"+userId;

		try {

			jedis = acquireJedisInstance();

			String json = gson.toJson(shoppingCart);
			jedis.set(userId_str, json);
			jedis.expire(userId_str, sessiondataTTL);

		} catch (Exception e) {
			logger.error("Error occured while storing data to the cache ", e.getMessage());
			releaseJedisInstance(jedis);
			throw new RuntimeException(e);

		} finally {
			releaseJedisInstance(jedis);
		}

		return shoppingCart;
	}

	public ShoppingCart retrieveShoppingCart(Long userId) {

		Jedis jedis = null;
        String userId_str = "Shopping_cart_"+userId;

		try {

			jedis = acquireJedisInstance();

			String cartJSON = jedis.get(userId_str);

			if (StringUtils.hasText(cartJSON)) {
				return gson.fromJson(cartJSON, ShoppingCart.class);
			}

		} catch (Exception e) {
			logger.error("Error occured while retrieving data from the cache ", e.getMessage());
			releaseJedisInstance(jedis);
			throw new RuntimeException(e);

		} finally {
			releaseJedisInstance(jedis);
		}

		return null;
	}

	public void flushShoppingCartCache(Long userId) {

		Jedis jedis = null;
        String userId_str = "Shopping_cart_"+userId;
		try {

			jedis = acquireJedisInstance();

			if (jedis.exists(userId_str)) {
				jedis.del(userId_str);
			}
		} catch (Exception e) {
			logger.error("Error occured while flushing specific data from the cache ", e.getMessage());
			releaseJedisInstance(jedis);
			throw new RuntimeException(e);

		} finally {
			releaseJedisInstance(jedis);
		}

	}

	public void clearAllCache() {

		Jedis jedis = null;
		try {

			jedis = acquireJedisInstance();
			jedis.flushAll();

		} catch (Exception e) {
			logger.error("Error occured while flushing all data from the cache ", e.getMessage());
			releaseJedisInstance(jedis);
			throw new RuntimeException(e);

		} finally {
			releaseJedisInstance(jedis);
		}

	}

}
