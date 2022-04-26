package com.sample.demo.ocr.service;

import java.io.File;
import java.util.List;

public interface OCRLocalService {
	public String oneFileProcess(String localPath);
	public boolean dirProcess(List<File> files);
	public List<File> imageFileList(String dirName);
}
