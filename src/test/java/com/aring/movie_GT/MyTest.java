package com.aring.movie_GT;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.aring.bean.Cart;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MyTest {

	//@Test
	public void testJson() throws Exception{
		String carts = 	URLDecoder.decode("%7B%22books%22%3A%5B%7B%22id%22%3A1%2C%22price%22%3A72.8%2C%22num%22%3A5%2C%22check%22%3Atrue%7D%5D%2C%22sum%22%3A364%7D", "utf-8");
		System.out.println(carts);
		ObjectMapper mapper = new ObjectMapper();
		Cart cart = mapper.readValue(carts,Cart.class);
		System.out.println(cart);
		
	}
	
	//@Test
	public void testJson2() throws Exception{
		String test = "[{\"id\": 1,\"str\":\"test\"}]";
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> list = mapper.readValue(test, new TypeReference<List<Map<String,String>>>(){});
		System.out.println(list);
	}
	
	@Test
	public void testSet(){
		Set<Integer> idSet = new HashSet<>();
		System.out.println(idSet.add(1));
		System.out.println(idSet.add(1));
		
	}
	
	
}
