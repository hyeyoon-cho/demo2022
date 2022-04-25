package com.sample.demo.ocr.service.impl;

import javax.annotation.PostConstruct;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.sample.demo.common.utils.RestUtil;
import com.sample.demo.ocr.service.OCRLocalService;

@Service("OCRLocalService")
public class OCRLocalServiceImpl implements OCRLocalService {

	@Value("${ocr.addr}")
	String ocrAddr;
	@Value("${ocr.sdk_url}")
	String sdk_url;
	
	@Value("${ocr.api_key}")
	String ocrApiKey;
	
	@Autowired
	RestUtil restUtil;
	
	
	/*
	StringBuffer payloadBuffer;
	@PostConstruct
	private void initOcrPayload() {
		payloadBuffer = new StringBuffer();
		payloadBuffer.append("api_key=")
		.append(ocrApiKey)
		.append("&textout=true");
	}
	
	private String getPayload(String type, String path) {
		payloadBuffer.append("&type=").append(type)
		.append("&path=").append(path);
		return payloadBuffer.toString();
	}
	*/
	JSONObject reqData;
	@PostConstruct
	private void initReqData() {
		reqData = new JSONObject();
		reqData.put("api_key", ocrApiKey);
		reqData.put("textout", "true");
	}
	private JSONObject getPayload(String type, String path) {
		reqData.put("type", type);
		reqData.put("path", path);
		return reqData;
	}
	
	public void oneFileProcess(String localPath) {
		String result = restUtil.requestPostMultipart("http://"+ocrAddr+sdk_url, getPayload("local", localPath));
		System.out.println(reqData.toJSONString());
		System.out.println(result);
	}
	
	
}
