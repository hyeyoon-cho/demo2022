package com.sample.demo.common.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@RunWith(SpringRunner.class)
class RestTemplateUtilTest {
	
	@Autowired
	RestTemplate restTemplate;
	
	String ocrAddr= "127.0.0.1:62975";
	String sdk_url = "/sdk/ocr";
	String ocrApiKey = "SNOCR-40afba5e91fd49b88224e9b76229c8af";

	@Test
	void test() {
		assertThat(restTemplate != null);
		
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://"+ocrAddr+sdk_url;
		
		
		for(String s : createList()) {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("api_key", ocrApiKey);
			map.add("textout", "true"); 
			map.add("type", "local"); 
			map.add("path", s); 
			
			String response
			  = restTemplate.postForObject(url, map, String.class);
			System.out.println(response);
		}
			
	}
	
	
	List<String> createList(){
		String prefix = "/home/konan/image/";
		List<String> list = Arrays.asList(
					prefix + "00a47901650f1f88d3e408f4b88aca21_1.jpg"
				, prefix + "012263e765e12e057a5e6c91933bf5f6_561.gif"
				);
		return list;
	}

}
