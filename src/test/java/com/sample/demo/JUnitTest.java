package com.sample.demo;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sample.demo.common.utils.JUnitTestUtils;


class JUnitTest {
	JUnitTestUtils utils;
	static JUnitTestUtils utils2;
	
	/**
	 * 테스트 전체에서 테스트 전 한번 수행되는 메소드
	 * static이어야 함 -> 바디에 선언되는 변수들도 물론 static 
	 */
	@BeforeAll
	public static void createBeforeAll() {
		System.out.println("beforeAll");
		utils2 = new JUnitTestUtils();
	}
	
	/**
	 * ~junit4 까지 @Before 로 사용했던..
	 * test코드마다 수행됨
	 * 모든 테스트 코드에 포함되어 있는 공통적인 초기화 코드
	 */
	@BeforeEach
	public void createBeforeEach() {
		System.out.println("beforeEach");
		utils = new JUnitTestUtils();
	}
	
	/**
	 * 단언
	 */

	@Test
	void test() {
		assertTrue(utils.getNumber() == 1);
	}

	@Test
	void test2() {
		assertThat(utils.getNumber() , is(1)); //is 장식자: 아무것도 안하지만 가독성용
	}
	
	/**
	 * 주요 햄크레스트 매처
	 * 
	 */
	@Test
	void test_hamcrest() {
		//컬렉션 객체 비교시 equalTo
		assertThat(new String[] {"a","b","c"}, equalTo(new String[] {"a","b","c"}));
		assertThat(new String[] {"a","b","c"}, not((new String[] {"a","b"})));
		
		//null값 검사
//		assertThat(utils.name, is(not(nullValue())));
		assertThat(utils.name, is(nullValue()));
		
	}

	/**
	 * exception test
	 */
//	@Test(expected=RuntimeException.class) //junit4에서의 방식
	@Test
	void test_exception() {
		//1. exception을 기대함 :: exception이 나야 테스트 통과
		assertThrows(RuntimeException.class, () -> {
			utils.number(0);
		});
		
		//2. try-catch 문을 이용해서 예외 발생하지 않으면 fail 처리 (옛 방식이라고함)
		// exception 메세지를 확인해야할 때 유용함
		try {
			utils.number(0);
			fail();
		}catch(RuntimeException e) {}
		
		//3. @Rule 애노테이션 사용 --> junit4에서 방식
		
	}
	
	
	/**
	 * 테스트 메소드명/테스트이름 작성
	 * given-when-then 양식사용
	 * ex) givenSomeContextWhenDoingSomeBehaviorThenSomeResultOccurs
	 * 길고 복잡할경우 given 제거 ==> whenDoingSomeBehaviorThenSomeResultOccurs
	 */
	
	
	
}
