package com.sample.demo.common.exception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class CommonExceptionTest {

	@Test
	void test() {
		CommonException e = new CommonException(500, ErrorCode.INVALID_INPUT_VALUE,"test");
		ObjectMapper mapper = new ObjectMapper();
		String result;
		try {
			result = mapper.writeValueAsString(e);
			System.out.println(result);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		assertThat(true);
	}

}
