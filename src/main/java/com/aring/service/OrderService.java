package com.aring.service;

import java.util.List;
import java.util.Map;

import com.aring.bean.Cart;
import com.aring.bean.Order;
import com.aring.bean.OrderBook;
import com.aring.bean.User;

public interface OrderService {
	
	/**
	 * 无效订单，status=0
	 */
	final public static String ORDER_UNABLE="unable";
	
	
	/**
	 * 等待取书 status=1
	 */
	final public static String ORDER_WAIT="wait";
	
	/**
	 * 订单完成 status=2
	 */
	final public static String ORDER_FINISHED="finished";
	
	final public static int ORDER_PAGE_SIZE = 10;
	

	/**
	 * 保存购物车，生成订单
	 * @param user
	 * @param cart
	 * @return 返回订单id
	 */
	public String saveCart(User user,Cart cart) throws Exception;
	
	/**
	 * 更新订单信息
	 * @param orders json格式
	 * @return
	 * @throws Exception
	 */
	public void updateOrderInfo(String orders) throws Exception;
	
	public void saveOrder(Order order) throws Exception;
	
	public void saveOderBook(OrderBook orderBook) throws Exception;
	
	public Order getOrderByPrimaryKey(Integer key);
	
	public void updateOrder(Order order) throws Exception;
	
	/**
	 * 获取订单的相关书籍信息
	 * @param oid 订单ID
	 * @return
	 */
	public List<OrderBook> listOrderBookByOid(Integer oid);
	
	/**
	 * 根据状态获取用户的订单
	 * @param state
	 * @param uid
	 * @return
	 */
	public List<Order> listOrderByState(String state,int uid,int page) throws Exception;
	
	public Map<String,Long> countOrderByState(String state,int uid,int page) throws Exception;
	
	
}
