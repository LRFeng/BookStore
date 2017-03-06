package com.aring.service;

import java.util.List;

import com.aring.bean.Cart;
import com.aring.bean.Order;
import com.aring.bean.OrderBook;
import com.aring.bean.User;

public interface OrderService {

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
	
}
