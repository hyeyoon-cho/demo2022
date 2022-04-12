package com.sample.demo.common.utils;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sample.demo.common.exception.CommonException;

public class RestUtil {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public Integer HTTP_GET = 1;
	public Integer HTTP_POST = 2;
	public Integer HTTP_PUT = 3;
	public Integer HTTP_DELETE = 4;
	public String CHARSET = Charset.defaultCharset().name();
	public Integer TIMEOUT = 10000;

	public String requestGet(String url) {
		return request(url, null , HTTP_GET,CHARSET,true,TIMEOUT);
	}
	public String requestPost(String url, String body) {
		return request(url, body , HTTP_POST,CHARSET,true,TIMEOUT);
	}
	private String request(String targetURL, String payload, int httpMethod, String charset,
			boolean useSocketTimeout, int timeout) {

		Builder builder = RequestConfig.custom().setConnectTimeout(timeout).setConnectionRequestTimeout(timeout);
		if (useSocketTimeout) {
			builder.setSocketTimeout(timeout);
		}
		RequestConfig config = builder.build();
		CloseableHttpClient httpclient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
		logger.debug("request url >> {}", targetURL);

		String result;
		try {
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				@Override
				public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					HttpEntity entity = response.getEntity();
					if ((status >= 200 && status < 300) || status == 400) {
						return entity != null ? EntityUtils.toString(entity, charset) : null;
					} else {
						throw new CommonException(status, EntityUtils.toString(entity, charset));
					}
				}
			};

			String responseBody;
			if (httpMethod == HTTP_POST) {
				HttpPost http = new HttpPost(targetURL);
				http.setEntity(new StringEntity(payload, charset));
				responseBody = (String) httpclient.execute(http, responseHandler);
			} else if (httpMethod == HTTP_PUT) {
				HttpPut http = new HttpPut(targetURL);
				http.setHeader("Accept", "application/json");
				http.setHeader("Content-type", "text/plain; charset=" + charset);
				if (payload != null) {
					http.setEntity(new StringEntity(payload, charset));
				}
				responseBody = (String) httpclient.execute(http, responseHandler);
			} else if (httpMethod == HTTP_DELETE) {
				HttpDelete http = new HttpDelete(targetURL);
				responseBody = (String) httpclient.execute(http, responseHandler);
			} else {
				HttpGet http = new HttpGet(targetURL);
				responseBody = (String) httpclient.execute(http, responseHandler);
			}

			logger.trace("response body >> {}", responseBody);
			result = responseBody;
		} catch (CommonException e) {
			throw e;
		} catch (ConnectException e) {
			throw new CommonException(-3, e.getMessage());
		} catch (ConnectTimeoutException e) {
			throw new CommonException(-2, e.getMessage());
		} catch (SocketTimeoutException e) {
			throw new CommonException(-2, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommonException(e.getMessage());
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		return result;
	}

}
