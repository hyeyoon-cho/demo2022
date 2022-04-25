package com.sample.demo;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Demo2022ApplicationTests {

	@Test
	void contextLoads() {
		JSONObject obj = new JSONObject();
		obj.put("1", "!");
		
		System.out.println(obj.toJSONString());
	}

}
