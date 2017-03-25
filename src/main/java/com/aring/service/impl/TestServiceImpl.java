package com.aring.service.impl;

import org.springframework.stereotype.Service;

import com.aring.service.TestService;

@Service("testService")
public class TestServiceImpl implements TestService{

	public TestServiceImpl() {
		System.out.println("init--->");
	}
	
	
	@Override
	public String holle(String word) {
		return "holle "+word;
	}

}
