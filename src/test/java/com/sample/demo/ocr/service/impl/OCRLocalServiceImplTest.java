package com.sample.demo.ocr.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class OCRLocalServiceImplTest {
	
	OCRLocalServiceImpl service = new OCRLocalServiceImpl();
	
	@Test
	void test() {
		int count = service.imageFileList("C:\\Users\\hyeyoon.cho").size();
		assertThat(count > 0);
	}
	
	@Test
	void serverTest2() {
		boolean isOK = service.dirProcess(createList());
		assertThat(isOK);
	}
	
	List<File> createList() {
		String prefix = "/home/konan/image/";
		List<File> list = Arrays.asList(
					new File(prefix + "00a47901650f1f88d3e408f4b88aca21_1.jpg")
				, new File(prefix + "012263e765e12e057a5e6c91933bf5f6_561.gif")
				, new File(prefix + "012263e765e12e057a5e6c91933bf5f6_563.png")
				, new File(prefix + "012263e765e12e057a5e6c91933bf5f6_564.png")
				, new File(prefix + "012263e765e12e057a5e6c91933bf5f6_566.png")
				, new File(prefix + "012263e765e12e057a5e6c91933bf5f6_599.gif")
				, new File(prefix + "012263e765e12e057a5e6c91933bf5f6_602.gif")
				);
		
		
		return list;
	}

}
