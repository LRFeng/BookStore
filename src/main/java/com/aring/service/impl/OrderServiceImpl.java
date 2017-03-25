package com.aring.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aring.bean.Book;
import com.aring.bean.BookPO;
import com.aring.bean.Cart;
import com.aring.bean.Order;
import com.aring.bean.OrderBook;
import com.aring.bean.User;
import com.aring.service.OrderService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class OrderServiceImpl implements OrderService{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public String saveCart(User user, Cart cart) throws Exception{
		
		Map<Integer,List<Book>> map = new HashMap<>();
		Map<Integer,BookPO> bookPoMap = new HashMap<>();
		StringBuffer oids = new StringBuffer();
		//按店铺整理教材
		for (BookPO bookPO : cart.getBooks()) {
			Book book = em.find(Book.class,bookPO.getId());
			bookPoMap.put(book.getId(),bookPO);
			List<Book> books = map.get(book.getSid());
			if(books==null || books.size()==0){
				books = new ArrayList<>();
				books.add(book);
				map.put(book.getSid(), books);
			}else{
				books.add(book);
			}
		}
		//按店铺生成订单
		for(Integer key:map.keySet()){
			Order order = new Order();
			List<Book> books = map.get(key);
			order.setStatus(0);
			order.setSid(key);
			order.setUid(user.getId());
			order.setCreateDate(new Date());
			saveOrder(order);
			oids.append(order.getId()).append(":");
			//保存订单教材关系
			for (Book book : books) {
				BookPO bookPO = bookPoMap.get(book.getId());
				OrderBook orderBook = new OrderBook();
				orderBook.setCreatDate(new Date());
				orderBook.setNumber(bookPO.getNum());
				orderBook.setStatus(0);
				orderBook.setSum(bookPO.getNum()*book.getNewPrice());
				orderBook.setPrice(book.getNewPrice());
				orderBook.setOid(order.getId());
				orderBook.setBid(book.getId());
				saveOderBook(orderBook);
			}
		}
		oids.deleteCharAt(oids.length()-1);
		return oids.toString();
	}

	@Override
	@Transactional
	public void saveOrder(Order order) throws Exception {
		if(order!=null){
			em.persist(order);
		}
	}

	@Override
	@Transactional
	public void saveOderBook(OrderBook orderBook) throws Exception {
		if(orderBook!=null){
			em.persist(orderBook);
		}
	}

	@Override
	public Order getOrderByPrimaryKey(Integer key) {
		return em.find(Order.class, key);
	}

	@Override
	public List<OrderBook> listOrderBookByOid(Integer oid) {
		String sql = "select ob from OrderBook ob where ob.oid="+oid;
		TypedQuery<OrderBook> query = em.createQuery(sql, OrderBook.class);
		return query.getResultList();
	}

	@Override
	@Transactional
	public void updateOrder(Order order) throws Exception {
		if(order!=null){
			em.merge(order);
		}
	}

	
	@Override
	@Transactional
	public void updateOrderInfo(String orders) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		List<HashMap<String, String>> list =
				mapper.readValue(orders, new TypeReference<List<Map<String,String>>>(){});
		for (HashMap<String, String> map : list) {
			Integer oid = Integer.valueOf(map.get("oid"));
			Date startDate = format.parse(map.get("start"));
			Date endDate = format.parse(map.get("end"));
			String message = map.get("message");
			Order order = em.find(Order.class,oid);
			order.setStatus(1);
			order.setExtractStartDate(startDate);
			order.setExtractEndDate(endDate);
			order.setUserMessage(message);
			em.merge(order);
		}
	}

	@Override
	public List<Order> listOrderByState(String state, int uid,int page) {
		StringBuffer sql = new StringBuffer("select t from Order t where t.uid=");
		sql.append(uid).append(" and");
		if(null == state){
			state="";
		}
		switch (state) {
		case ORDER_WAIT:
			sql.append(" t.status=1");
			break;
		case ORDER_FINISHED:
			sql.append(" t.status=2");
			break;
		case ORDER_UNABLE:
			sql.append(" t.status=0");
			break;
		default:
			sql.append(" (t.status=1 or t.status=2)");
			break;
		}
		sql.append(" order by t.createDate desc");
		TypedQuery<Order> query = em.createQuery(sql.toString(),Order.class);
		query.setFirstResult(ORDER_PAGE_SIZE*(page-1));
		query.setMaxResults(ORDER_PAGE_SIZE);
		return query.getResultList();
	}
	
	@Override
	public Map<String,Long> countOrderByState(String state, int uid, int page) throws Exception {
		StringBuffer sql = new StringBuffer("select count(t.id) from Order t where t.uid=");
		sql.append(uid).append(" and");
		if(null == state){
			state="";
		}
		switch (state) {
		case ORDER_WAIT:
			sql.append(" t.status=1");
			break;
		case ORDER_FINISHED:
			sql.append(" t.status=2");
			break;
		case ORDER_UNABLE:
			sql.append(" t.status=0");
			break;
		default:
			sql.append(" (t.status=1 or t.status=2)");
			break;
		}
		Query query = em.createQuery(sql.toString());
		Long count = (Long) query.getSingleResult();
		Map<String,Long> pager = new HashMap<>();
		pager.put("count", count);
		if(count==0){
			pager.put("countPage",0L);
			pager.put("currPage",0L);
		}else{
			pager.put("countPage",count%ORDER_PAGE_SIZE==0?count/ORDER_PAGE_SIZE:(count/ORDER_PAGE_SIZE+1));
			pager.put("currPage",Long.valueOf(page));
		}
		
		return pager;
	}

}
