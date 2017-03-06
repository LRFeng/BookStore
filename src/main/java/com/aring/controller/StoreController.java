package com.aring.controller;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.aring.bean.Book;
import com.aring.bean.BookPO;
import com.aring.bean.Cart;
import com.aring.bean.Order;
import com.aring.bean.OrderBook;
import com.aring.bean.Post;
import com.aring.bean.Specialist;
import com.aring.bean.Store;
import com.aring.bean.User;
import com.aring.bean.UserInfo;
import com.aring.exception.StoreException;
import com.aring.service.BookService;
import com.aring.service.CommonService;
import com.aring.service.OrderService;
import com.aring.service.PostService;
import com.aring.service.StoreService;
import com.aring.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class StoreController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private StoreService storeService;
	
	@Autowired
	private PostService postService;
	
	
	@RequestMapping("/")
	public ModelAndView indexPage(HttpSession session) throws Exception{
		ModelAndView mav = new ModelAndView("store/index");
		User user = (User) session.getAttribute("user");
		if(user!=null) mav.addObject("user", user);
		List<Book> books = bookService.listNewestForIndex();
		mav.addObject("books", books);
		return mav;
	}
	
	
	@RequestMapping("/register")
	public String registerPage(){
		return "store/register";
	}
	
	@RequestMapping("/do-register")
	public void register(HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String message = null;
		boolean success = false;
		try {
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String password2 = request.getParameter("password2");
			if(name==null || name.equals("")){
				message = "昵称 不能为空";
			}else if(email==null || email.equals("")){
				message = "邮件地址不能为空";
			}else if(password==null || password.equals("")){
				message = "密码不能为空";
			}else if(!password.equals(password2)){
				message = "确认密码不能为空";
			}else{
				User user = new User();
				user.setName(name);
				user.setEmail(email);
				user.setPassword(password);
				user.setType(1);
				String avatar =	request.getParameter("avatar");
				Integer fid = null;
				if(avatar!=null && !"".equals(avatar)){
					fid = Integer.valueOf(avatar);
				}
				userService.saveUser(user);
				UserInfo userInfo = new UserInfo();
				userInfo.setAddress(request.getParameter("address"));
				String year = request.getParameter("collegeYear");
				if(year!=null){
					userInfo.setCollegeYear(Integer.valueOf(year));
				}
				userInfo.setSchool(request.getParameter("school"));
				userInfo.setSpecialist(request.getParameter("specialist"));
				userInfo.setTelephone(request.getParameter("telephone"));
				userInfo.setUid(user.getId());
				userService.register(user, userInfo, fid);
				success = true;
				message = "注册成功";
			}
		}catch (StoreException e) {
			e.printStackTrace();
			success = false;
			message = e.getMessage();
		}catch (Exception e) {
			e.printStackTrace();
			success = false;
			message = "服务器异常";
		}finally {
			String result = "{success:"+success+",msg:\""+message+"\"}";
			out.println(result);
			out.close();
		}
	}
	
	@RequestMapping("/login")
	public String loginPage(HttpSession session){
		if(null == session.getAttribute("user")){
			return "store/login";
		}else{
			return "redirect:/";
		}
	}
	
	@RequestMapping("/do-login")
	public void login(HttpServletRequest request,HttpSession session,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		boolean success = false;
		String message = null;
		try {
			User user = userService.login(email, password);
			session.setAttribute("user", user);
			success = true;
			message ="验证成功";
		}catch (StoreException e) {
			e.printStackTrace();
			message = e.getMessage();
		}catch (Exception e) {
			e.printStackTrace();
			message ="服务器异常";
		}finally {
			String result = "{success:"+success+",msg:\""+message+"\"}";
			out.println(result);
			out.close();
		}
	}
	
	@RequestMapping(value="/single")
	public ModelAndView singlePage(HttpSession session,Integer id) throws Exception{
		ModelAndView mav = new ModelAndView("store/single");
		User user = (User) session.getAttribute("user");
		if(user!=null) mav.addObject("user", user);
		Book book = bookService.getByPrimary(id);
		List<String> images = commonService.listImageUrls(BookService.BOOK_TABLE_NAME, id);
		mav.addObject("book",book);
		mav.addObject("images", images);
		return mav;
	}
	
	
	
	@RequestMapping(value="/search")
	public ModelAndView searchBook(HttpServletRequest request){
		ModelAndView mav = new ModelAndView("store/list");
		String spid = request.getParameter("spid");
		String keyword = request.getParameter("keyword");
		String sort = request.getParameter("sort");
		String page = request.getParameter("page");
		try {
			Integer spid2 = null;
			if(spid!=null&&!"".equals(spid)){
				spid2 = Integer.valueOf(spid);
				Specialist specialist = commonService.getSpecialistByPrimary(spid2);
				mav.addObject("specialist", specialist);
			}
			List<Book> books = bookService.listSearchBook(spid2,keyword, sort, Integer.valueOf(page));
			Map<String,Long> pager = bookService.countSearchBook(spid2,keyword, sort, Integer.valueOf(page));
			mav.addObject("books", books);
			mav.addObject("pager", pager);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping("/cart")
	public ModelAndView cart(HttpServletRequest request,HttpSession session) throws Exception{
		ModelAndView mav = new ModelAndView("/store/cart");
		Cart cart = getCartFromCookie(request);
		if(cart!=null){
			List<Book> books = new ArrayList<>();
			for(BookPO p:cart.getBooks()){
				Book book =	bookService.getByPrimary(p.getId());
				books.add(book);
			}
			mav.addObject("books",books);
			mav.addObject("cart", cart);
		}
		return mav;
	}
	
	@RequestMapping("reserve-book")
	public void reserveBook(HttpServletRequest request,HttpSession session,HttpServletResponse response)throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		boolean success = false;
		String message = null;
		try {
			User user = (User) session.getAttribute("user");
			if(user==null) throw new StoreException("未登录");
			
			Cart cart = getCartFromCookie(request);
			if(cart==null) throw new StoreException("购物车为空");
			String oids = saveCart(user, cart);
			success = true;
			message = oids;
		}catch (StoreException e) {
			e.printStackTrace();
			message =e.getMessage();
		}catch (Exception e) {
			e.printStackTrace();
			message ="服务器异常";
		}finally {
			String result = "{success:"+success+",msg:\""+message+"\"}";
			out.println(result);
			out.close();
		}
	}
	
	@RequestMapping("confirm-order")
	public ModelAndView confirmOrderPage(HttpServletRequest request,HttpSession session){
		ModelAndView mav = new ModelAndView();
		try {
			User user = (User) session.getAttribute("user");
			if(user==null) throw new StoreException("鏈櫥褰�");
			String oidStr = request.getParameter("oids");
			String[] oids = oidStr.split(":");
			List<Order> orders = new ArrayList<>();
			Map<Integer,List<OrderBook>> mapBookPos = new HashMap<>();
			Map<Integer,Book> mapBook = new HashMap<>();
			Map<Integer,Store> mapStore = new HashMap<>();
			for (String idStr : oids) {
				Integer id = Integer.valueOf(idStr);
				Order order = orderService.getOrderByPrimaryKey(id);
				if(order!=null){
					orders.add(order);
					mapStore.put(id,storeService.getStoreByPrimary(order.getSid()));
					List<OrderBook> bookPOs = orderService.listOrderBookByOid(id);
					for (OrderBook orderBook : bookPOs) {
						Book book = bookService.getByPrimary(orderBook.getBid());
						mapBook.put(orderBook.getBid(),book);
					}
					mapBookPos.put(id,bookPOs);
				}
			}
			mav.setViewName("store/reserve");
			mav.addObject("orders",orders);
			mav.addObject("mapBookPos",mapBookPos);
			mav.addObject("mapBook",mapBook);
			mav.addObject("mapStore",mapStore);
		}catch (StoreException e) {
			e.printStackTrace();
			mav.setViewName("redirect:/login");
		} catch (Exception e) {
			e.printStackTrace();
			mav.setViewName("redirect:/");
		}
		return mav;
	}
	
	@RequestMapping("do-confirm-order")
	public void confirmOrder(HttpServletRequest request,HttpServletResponse response)throws Exception{

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		boolean success = false;
		String message = null;
		try {
			User user = (User) request.getSession().getAttribute("user");
			if(user==null) throw new StoreException("未登录");
			String orders =request.getParameter("orders");
			if(orders!=null &&!"".equals(orders)){
				orderService.updateOrderInfo(orders);
			}
			success = true;
			message="保存成功";
		}catch (StoreException e) {
			e.printStackTrace();
			message =e.getMessage();
		}catch (Exception e) {
			e.printStackTrace();
			message ="服务器异常";
		}finally {
			String result = "{success:"+success+",msg:\""+message+"\"}";
			out.println(result);
			out.close();
		}
	}
	
	@RequestMapping("post")
	private ModelAndView postPage(HttpServletRequest request)throws Exception{
		ModelAndView mav = new ModelAndView("/store/post");
		String tag = request.getParameter("tag");
		String keyword = request.getParameter("keyword");
		String sort = request.getParameter("sort");
		String page = request.getParameter("page");
		Integer tagid = null;
		if(null!=tag && !"".equals(tag)){
			tagid = Integer.valueOf(tag);
		}
		Map<Integer,List<String>> urlMap = new HashMap<>();
		Map<Integer,User> userMap = new HashMap<>();
		List<Post> posts = postService.listSearchPost(tagid, keyword, sort, Integer.valueOf(page));
		Map<String,Long> pager = postService.countSearchPost(tagid, keyword, sort, Integer.valueOf(page));
		for (Post post : posts) {
			List<String> urls = commonService.listImageUrls(PostService.POST_TABLE_NAME,post.getId());
			User user = userService.getUserByPrimaryKey(post.getUid());
			userMap.put(post.getId(), user);
			urlMap.put(post.id,urls);
		}
		mav.addObject("userMap",userMap);
		mav.addObject("urlMap",urlMap);
		mav.addObject("posts", posts);
		mav.addObject("pager",pager);
		return mav;
	}
	
	
	@RequestMapping("public-post")
	public void publicPost(HttpServletRequest request,HttpServletResponse response)throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		boolean success = false;
		String message = null;
		try {
			User user = (User) request.getSession().getAttribute("user");
			if(user==null) throw new StoreException("未登录");
			String tag =request.getParameter("tag");
			String title =request.getParameter("title");
			String content =request.getParameter("content");
			String fids =request.getParameter("fids");
			if(tag==null || "".equals(tag)||title==null || "".equals(title)){
				throw new StoreException("参数不合法");
			}
			Post post = new Post();
			post.setTitle(title);
			post.setContent(content);
			post.setUid(user.getId());
			post.setTid(Integer.valueOf(tag));
			postService.savePost(post);
			if(null!=fids && !"".equals(fids)){
				postService.savePostImage(post.getId(), fids);
			}
			success = true;
			message="发布成功";
		}catch (StoreException e) {
			e.printStackTrace();
			message =e.getMessage();
		}catch (Exception e) {
			e.printStackTrace();
			message ="服务器异常";
		}finally {
			String result = "{success:"+success+",msg:\""+message+"\"}";
			out.println(result);
			out.close();
		}
	}
	
	public void likePost(HttpServletRequest request,HttpServletResponse response)throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		boolean success = false;
		String message = null;
		try {
			User user = (User) request.getSession().getAttribute("user");
			if(user==null) throw new StoreException("未登录");
			String pid =request.getParameter("pid");
			if(pid==null || "".equals(pid)){
				throw new StoreException("参数不合法");
			}
			postService.likePost(user.getId(),Integer.valueOf(pid));
			success = true;
			message="已点赞";
		}catch (StoreException e) {
			e.printStackTrace();
			message =e.getMessage();
		}catch (Exception e) {
			e.printStackTrace();
			message ="服务器异常";
		}finally {
			String result = "{success:"+success+",msg:\""+message+"\"}";
			out.println(result);
			out.close();
		}
	}
	
	private String saveCart(User user, Cart cart) throws Exception{
		Map<Integer,List<Book>> map = new HashMap<>();
		Map<Integer,BookPO> bookPoMap = new HashMap<>();
		StringBuffer oids = new StringBuffer();
		for (BookPO bookPO : cart.getBooks()) {
			Book book = bookService.getByPrimary(bookPO.getId());
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
		for(Integer key:map.keySet()){
			int count =0 ;
			double sum = 0;
			Order order = new Order();
			List<Book> books = map.get(key);
			order.setStatus(0);
			order.setSid(key);
			order.setUid(user.getId());
			order.setCreateDate(new Date());
			orderService.saveOrder(order);
			oids.append(order.getId()).append(":");
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
				orderService.saveOderBook(orderBook);
				count+=bookPO.getNum();
				sum+=orderBook.getSum();
			}
			order.setCount(count);
			order.setSum(sum);
			orderService.updateOrder(order);
		}
		oids.deleteCharAt(oids.length()-1);
		return oids.toString();
	}
	
	
	private Cart getCartFromCookie(HttpServletRequest request) throws Exception{
		Cookie[] cookies = request.getCookies();
		Cart cart = null;
		for (Cookie cookie : cookies) {
			if("cart".equals(cookie.getName())){
				String carts = 	URLDecoder.decode(cookie.getValue(), "utf-8");
				ObjectMapper mapper = new ObjectMapper();
				cart = mapper.readValue(carts,Cart.class);
			}
		}
		return cart;
	}
	
	
}
