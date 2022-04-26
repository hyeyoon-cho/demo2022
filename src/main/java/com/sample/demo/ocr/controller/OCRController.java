package com.sample.demo.ocr.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sample.demo.ocr.service.OCRLocalService;

@Controller
public class OCRController {

	
	@Autowired
	OCRLocalService service;
	
	@PostMapping("/ocrTest")
	@ResponseBody
	public String fileOCRTest() {
		
		List<File> files = service.imageFileList("/home/konan/image2");
		service.dirProcess(files);
		
		return "OK";
		
	}
}
