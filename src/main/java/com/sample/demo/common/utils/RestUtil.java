package com.sample.demo.common.utils;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sample.demo.common.exception.CommonException;

@Component
public class RestUtil {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public Integer HTTP_GET = 1;
	public Integer HTTP_POST = 2;
	public Integer HTTP_PUT = 3;
	public Integer HTTP_DELETE = 4;
	public Integer HTTP_POST_WITHJSON = 5;
	public Integer HTTP_POST_MULTIPART = 6;
	public String CHARSET = Charset.defaultCharset().name();
	public Integer TIMEOUT = 10000;

	class CommonExecuteRequest implements ExecuteRequest {
		String targetURL;
		String payload;
		Integer httpMethod;
		String charset;
		Integer timeout;
		boolean useSocketTimeout;
		HttpClient httpclient;
		ResponseHandler responseHandler;

		public CommonExecuteRequest(String url, String body, Integer method, String charset, boolean useSocketTimeout,
				Integer timeout) {
			this.targetURL = url;
			this.payload = body;
			this.httpMethod = method;
			this.charset = charset;
			this.useSocketTimeout = useSocketTimeout;
			this.timeout = timeout;
		}

		@Override
		public void initHttpClient(HttpClient client, ResponseHandler handler) {
			this.httpclient = client;
			this.responseHandler = handler;
		}

		@Override
		public String execute()
				throws CommonException, ConnectException, ConnectTimeoutException, SocketTimeoutException, Exception {

			String responseBody;
			logger.debug("request url >> {}", targetURL);

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
			} else if (httpMethod == HTTP_POST_WITHJSON) {
				HttpPost http = new HttpPost(targetURL);
				StringEntity entity = new StringEntity(payload);
				http.setEntity(entity);
				http.setHeader("Accept", "application/json");
				http.setHeader("Content-type", "application/json");
				responseBody = (String) httpclient.execute(http, responseHandler);
			} else {
				HttpGet http = new HttpGet(targetURL);
				responseBody = (String) httpclient.execute(http, responseHandler);
			}
			return responseBody;
		}

	}

	public String requestGet(String url) {
		return request(CHARSET, true, TIMEOUT, new CommonExecuteRequest(url, null, HTTP_GET, CHARSET, true, TIMEOUT));
	}

	public String requestPost(String url, String body) {
		return request(CHARSET, true, TIMEOUT, new CommonExecuteRequest(url, null, HTTP_POST, CHARSET, true, TIMEOUT));
	}

	public String requestPostWithJson(String url, JSONObject json) {
		return request(CHARSET, true, TIMEOUT,
				new CommonExecuteRequest(url, null, HTTP_POST_WITHJSON, CHARSET, true, TIMEOUT));
	}

	public String requestPostMultipart(String url, JSONObject json) {
		Iterator<String> keys = json.keySet().iterator();
		
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		while(keys.hasNext()) {
			String k = keys.next();
			builder.addTextBody(k, (String) json.get(k));
		}
		
		return request(CHARSET, true, TIMEOUT, new ExecuteRequest() {
			HttpClient httpclient;
			ResponseHandler responseHandler;

			@Override
			public void initHttpClient(HttpClient client, ResponseHandler handler) {
				this.httpclient = client;
				this.responseHandler = handler;
			}

			@Override
			public String execute() throws CommonException, ConnectException, ConnectTimeoutException,
					SocketTimeoutException, Exception {
				String responseBody;
				CloseableHttpClient client = HttpClients.createDefault();
				HttpPost httpPost = new HttpPost(url);
				HttpEntity multipart = builder.build();
				httpPost.setEntity(multipart);
				responseBody = (String) httpclient.execute(httpPost, responseHandler);
				
				return responseBody;
			}
		});
	}

	private String request(String charset, boolean useSocketTimeout, int timeout, ExecuteRequest execute) {

		Builder builder = RequestConfig.custom().setConnectTimeout(timeout).setConnectionRequestTimeout(timeout);
		if (useSocketTimeout) {
			builder.setSocketTimeout(timeout);
		}
		RequestConfig config = builder.build();
		CloseableHttpClient httpclient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
		String result = "";
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
			execute.initHttpClient(httpclient, responseHandler);
			result = execute.execute();
			logger.trace("response body >> {}", result);
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

interface ExecuteRequest {
	public void initHttpClient(HttpClient client, ResponseHandler handler);

	public String execute()
			throws CommonException, ConnectException, ConnectTimeoutException, SocketTimeoutException, Exception;
}