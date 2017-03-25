package com.aring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 教材管理系统控制器
 * @author aring
 *
 */
@RequestMapping("/management")
@Controller
public class BookController {
	
	@RequestMapping("/")
	public String index(){
		return "/book/index";
	}
	
	@RequestMapping("/book")
	public String bookManagement(){
		return "/book/bookmanagement";
	}
	
	@RequestMapping("/menu")
	public String menuManagement(){
		return "/book/menu";
	}
	

}
