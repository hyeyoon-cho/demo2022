package com.sample.demo.ocr.controller;

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
		service.oneFileProcess("/home/konan/test.pdf");
		return "";
		
	}
}
