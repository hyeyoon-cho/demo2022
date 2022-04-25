package com.sample.demo.common.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Duration;
import java.util.Collections;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Component
public class RestTemplateUtil {
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {

		return restTemplateBuilder // 로깅 인터셉터에서 Stream을 소비하므로 BufferingClientHttpRequestFactory 을 꼭 써야한다.
				.requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
				// 타임아웃 설정 (ms 단위)
				.setConnectTimeout(Duration.ofMillis(30000)).setReadTimeout(Duration.ofMillis(30000))
				// UTF-8 인코딩으로 메시지 컨버터 추가
				.additionalMessageConverters(new StringHttpMessageConverter(Charset.forName("UTF-8")))
				// 로깅 인터셉터 설정
				.additionalInterceptors(new ClientHttpLoggingInterceptor()).build();

	}
}
/**
 * body는 Stream이므로 data를 읽으면 소비되어 body가 사라짐 --> BufferingClientHttpRequestFactory 세팅하여 해결
 * ex. 요청/응답 로그를 더 자세히 남기기 위하거나 추가적인 처리를 위해 사용 
 *
 */
@Slf4j
class ClientHttpLoggingInterceptor implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {

		// request 전
		beforeRequest(request, body);
		ClientHttpResponse response = execution.execute(request, body);
		// request 후
		afterRequest(response, body);

		// ex) header 값 추가할 수 있음
		response.getHeaders().add("add_header", "test");

		return null;
	}

	private void beforeRequest(HttpRequest request, byte[] body) {
		//log 쓰기
		log.debug("before");
	}

	private void afterRequest(ClientHttpResponse response, byte[] body) throws IOException {
		log.debug("after");
		log.debug(response.getHeaders().toString());
		log.debug(response.getStatusCode().toString());
		log.debug(response.getStatusText());
		log.debug(StreamUtils.copyToString(response.getBody(),Charset.defaultCharset()));
	}

}