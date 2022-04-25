package com.sample.demo.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SearchUtil {

	private final static String KS_URL = "http://%s/search?select=%s&from=%s&where=%s&charset=%s&offset=%d&limit=%d&default-hilite=off&score=abs";
	@Value("${crz.charset}")
	String ksCharset;
	
	public String getSearchURL(String addr, String fields, String volume, String table, String where, String order,
			int offset, int pagelength, String synDomainNumber) throws UnsupportedEncodingException {
		String url = "";
		if (StringUtils.isNotEmpty(where) || StringUtils.isNotEmpty(order)) {
			where = where + " " + order;
			where = URLEncoder.encode(where, ksCharset);
		}

		if (StringUtils.isNotEmpty(synDomainNumber)) {
			url = url + "&syn-domain-no=" + synDomainNumber;
		}
		
		url = String.format(url, addr, fields, volume + "." + table, where, ksCharset, offset, pagelength);
		System.out.println("url :: " + url);
		
		return url;
	}
	
	
	
	
}
