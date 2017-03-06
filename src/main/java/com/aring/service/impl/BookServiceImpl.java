package com.aring.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aring.bean.Book;
import com.aring.service.BookService;
import com.aring.utils.AppConfig;

@Service
public class BookServiceImpl implements BookService{

	@Autowired
	private AppConfig appConfig;
	
	@PersistenceContext
	private EntityManager em;
	
	
	@Override
	public List<Book> listNewestForIndex() throws Exception {
		String sql ="select b.id,b.name,b.mianPicUrl,b.newPrice,b.oldPrice,b.remainder from Book b order by b.updateTime";
		TypedQuery<Book> query =em.createQuery(sql,Book.class);
		query.setMaxResults(NEWEST_BOOK_NUMBER);
		List<Book> books = query.getResultList();
		for (Book book : books) {
			book.setMianPicUrl(appConfig.getFileServerDomain()+book.getMianPicUrl());
		}
		return books;
	}


	@Override
	public Book getByPrimary(int id) throws Exception {
		Book book = em.find(Book.class, id);
		book.setMianPicUrl(appConfig.getFileServerDomain()+book.getMianPicUrl());
		return book;
	}


	@Override
	public List<Book> listResembleBook(int number,Book book) throws Exception {
		
		
		return null;
	}


	
	
	@Override
	public List<Book> listSearchBook(Integer spid,String keywords, String sort, int page) throws Exception {
		String sql = searchBookSql(spid,keywords, sort);
		TypedQuery<Book> query = em.createQuery(sql, Book.class);
		query.setFirstResult((page-1)*BOOK_PAGE_SIZE);
		query.setMaxResults(BOOK_PAGE_SIZE);
		List<Book> books = query.getResultList();
		for(Book book:books){
			book.setMianPicUrl(appConfig.getFileServerDomain()+book.getMianPicUrl());
		}
		return books;
	}	
	
	
	
	@Override
	public Map<String, Long> countSearchBook(Integer spid,String keywords, String sort, int page) throws Exception {
		String sql = searchBookSql(spid,keywords, sort);
		sql = sql.replaceFirst("b","COUNT(b.id)");
		Query query = em.createQuery(sql);
		Long count = (Long) query.getSingleResult();
		Map<String,Long> pager = new HashMap<>();
		pager.put("count", count);
		if(count==0){
			pager.put("countPage",0L);
			pager.put("currPage",0L);
		}else{
			pager.put("countPage",count%BOOK_PAGE_SIZE==0?count/BOOK_PAGE_SIZE:(count/BOOK_PAGE_SIZE+1));
			pager.put("currPage",Long.valueOf(page));
		}
		
		return pager;
	}
	
	
	private String searchBookSql(Integer spid,String keywords, String sort){
		//查询规则：按照字段优先级，name>author>version>press,依次查询
		StringBuffer sql = new StringBuffer("select b from Book b,BookSpecialist bs where b.status=1 and ");		
		if(spid!=null){
			sql.append("bs.bid=b.id and bs.spid=").append(spid).append(" and ");
		}
		
		if(keywords==null || "".equals(keywords)){
			sql.append("(UPPER(b.name) like UPPER('%").append("").append("%') ")
		       .append(") ");
		}else{
			String[] words = keywords.split(" ");
			for (String str : words) {
				sql.append("(UPPER(b.name) like UPPER('%").append(str).append("%') ")
				   .append("or UPPER(b.author) like UPPER('%").append(str).append("%') ")
				   .append("or UPPER(b.version) like UPPER('%").append(str).append("%') ")
			       .append("or UPPER(b.press) like UPPER('%").append(str).append("%') ")
			       .append(") ");
			}
		}
		
		String[] sorts = sort.split(":");
		sql.append("order by b.");
		sql.append(sorts[0]).append(" ");
		if("0".equals(sorts[1])){
			sql.append("desc ");
		}
		return sql.toString();
	}
	
}
