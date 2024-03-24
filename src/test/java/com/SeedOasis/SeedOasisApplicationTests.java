package com.SeedOasis;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
class SeedOasisApplicationTests {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Test
	void contextLoads() {
	}

	@Test
	@DisplayName("String 테스트")
	void test() {
		//given
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		String key = "strKey";

		//when
		valueOperations.set(key, "hello");

		//then
		String value = valueOperations.get(key);
		System.out.println(value);
		Assertions.assertThat(value).isEqualTo("hello");

	}

}
