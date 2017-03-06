package com.aring.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.aring.bean.PostTag;
import com.aring.bean.Specialist;
import com.aring.service.CommonService;

public class CommHandlerInterceptor implements HandlerInterceptor{

	@Autowired
	private CommonService commonService;
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if(modelAndView!=null){
			List<Specialist> specialists = commonService.listHotSpecialist(CommonService.HOT_SPECIALIST_SIZE);
			List<PostTag> postTags = commonService.listPostTag();
			modelAndView.addObject("specialists", specialists);
			modelAndView.addObject("postTags", postTags);
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return true;
	}
	
}