package com.sample.demo.common.utils;

public class JUnitTestUtils {
	public String name;
	
	public int getNumber() {
		return 1;
	}
	
	public String getName() {
		return "Nana";
	}
	 
	
	public void number(int i) throws RuntimeException {
		if(i < 1)
			throw new RuntimeException("The number is smaller than 1");
	}
}
