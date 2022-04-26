package com.sample.demo.ocr.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.sample.demo.common.utils.RestUtil;
import com.sample.demo.ocr.service.OCRLocalService;

import lombok.extern.slf4j.Slf4j;

@Service("OCRLocalService")
@Slf4j
public class OCRLocalServiceImpl implements OCRLocalService {

	@Value("${ocr.addr}")
	String ocrAddr;
	@Value("${ocr.sdk_url}")
	String sdk_url;
	
	@Value("${ocr.api_key}")
	String ocrApiKey;
	
	@Autowired
	RestUtil restUtil;
	
	private static final int THREAD_COUNT = 40;

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
	
	public String oneFileProcess(String localPath) {
		String result = restUtil.requestPostMultipart("http://"+ocrAddr+sdk_url, getPayload("local", localPath));
//		System.out.println(result);
		return result;
	}
	
	public List<File> imageFileList(String dirName) {
		List<File> list = new ArrayList<File>();
		list = (ArrayList<File>) FileUtils.listFiles(new File(dirName), null, false);
		System.out.println(list.size());
		return list;
	}
	
	public boolean dirProcess(List<File> files) {
		ExecutorService threadService = Executors.newFixedThreadPool(THREAD_COUNT);
		ArrayList<Callable<Boolean>> callable = new ArrayList<Callable<Boolean>>();
		for(File f : files) {
			callable.add(getExecutor(f));
		}
		try {
			System.out.println(">>>>> START :: " + System.currentTimeMillis());
			threadService.invokeAll(callable);
			System.out.println(">>>>> END :: " + System.currentTimeMillis());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		threadService.shutdown();
		return threadService.isShutdown();
	}
	
	private Callable<Boolean> getExecutor(File file){
		return new Callable<Boolean>() {

			@Override
			public Boolean call() throws Exception {
//				System.out.println(Thread.currentThread().getName() +" :: " + file.getAbsolutePath());
				oneFileProcess(file.getAbsolutePath());
				return true;
			}
			
		};
	}
	
}
