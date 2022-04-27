package com.sample.demo.common.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ExceptionResolver extends AbstractHandlerExceptionResolver {

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		
		if(ex instanceof CommonException) {
			try {
				return setResponse(request, response, (CommonException) ex);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}

	
	private ModelAndView setResponse(HttpServletRequest request, HttpServletResponse response, CommonException e) throws IOException {
        ModelAndView mv = new ModelAndView("jsonView");
        mv.addObject("result", e);
        return mv;
        
	}
}
