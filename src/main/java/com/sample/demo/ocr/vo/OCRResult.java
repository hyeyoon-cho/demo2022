package com.sample.demo.ocr.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OCRResult {

	String text;
	int page_index;
	int total_pages;
	String csv_file_name;
	String final_file_name;
	
}
