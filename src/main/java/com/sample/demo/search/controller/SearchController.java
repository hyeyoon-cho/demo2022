package com.sample.demo.search.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sample.demo.common.utils.RestUtil;
import com.sample.demo.common.utils.SearchUtil;
import com.sample.demo.search.service.SearchService;
import com.sample.demo.search.vo.ResultVO;

@RestController
public class SearchController {

	@Autowired
	SearchUtil searchUtil;
	@Autowired
	RestUtil restUtil;
	
	@Value("${engine.search.addr}")
	String engineAddr;
	
	@Autowired
	@Qualifier("KSService")
	SearchService searchService;
	
	
	
	@GetMapping("/search")
	public ResultVO main(@RequestParam(name="page", defaultValue="10") String page,
			@RequestParam(name="offset", defaultValue="1") String offset
			) throws Exception {
		page = "1000";
		String url = searchUtil.getSearchURL(engineAddr, "*", "review", "review", 
				"", "", Integer.parseInt(offset), Integer.parseInt(page),"");
		
		ResultVO vo = searchService.search(url);
		
		
		return vo;
	}
}
