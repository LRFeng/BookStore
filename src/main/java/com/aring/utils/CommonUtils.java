package com.aring.utils;

public class CommonUtils {
	
	/**
	 * 随机数字符生成器
	 * @param n 字符个数
	 * @return
	 */
	public static String strRandomer(int n){
		String returnVal ="";
		for (int i = 0; i < n; i++) {
			int val	= (int) (Math.random()*10);
			returnVal +=val;
		}
		return returnVal;
	}
	
	
}
