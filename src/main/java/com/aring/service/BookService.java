package com.aring.service;

import java.util.List;
import java.util.Map;

import com.aring.bean.Book;

public interface BookService {

	
	/**
	 * 首页最新教材数量
	 */
	final public static int NEWEST_BOOK_NUMBER = 20;
	
	/**
	 * 数据库教材表名称
	 */
	final public static String BOOK_TABLE_NAME="book";
	
	/**
	 * 每页大小
	 */
	final public static int BOOK_PAGE_SIZE = 12;
	
	
	/**
	 * 获取首页的最新教材
	 * @return
	 * @throws Exception
	 */
	public List<Book> listNewestForIndex() throws Exception;
	
	public Book getByPrimary(int id) throws Exception;
	
	/**
	 * 获取相似的教材
	 * @param number 数量
	 * @param book 原产品
	 * @return
	 * @throws Exception
	 */
	public List<Book> listResembleBook(int number,Book book) throws Exception;
	
	/**
	 * 教材查询接口
	 * @param spid 专业ID
	 * @param keywords 查询关键词
	 * @param sort 排序规则   price:0 [price]为排序字段标识；[:]分隔符；[0]是否升序，0是false，1是true
	 * @param page	当前页面
	 * @return
	 * @throws Exception
	 */
	public List<Book> listSearchBook(Integer spid,String keywords,String sort, int page) throws Exception;
	
	/**
	 * 计数查询结果
	 * @param keywords
	 * @param sort
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Map<String,Long> countSearchBook(Integer spid,String keywords,String sort, int page) throws Exception;
	
	
	
}
