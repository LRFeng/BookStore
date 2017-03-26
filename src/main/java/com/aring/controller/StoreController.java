package com.aring.controller;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.aring.bean.Comment;
import com.aring.bean.Order;
import com.aring.bean.OrderBook;
import com.aring.bean.Post;
import com.aring.bean.Specialist;
import com.aring.bean.Store;
import com.aring.bean.User;
import com.aring.bean.UserInfo;
import com.aring.exception.StoreException;
import com.aring.service.BookService;
import com.aring.service.CommentService;
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
	
	@Autowired
	private CommentService commentService;
	
	
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
				message = "密码不一致";
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
	
	@RequestMapping("logout")
	public ModelAndView logout(HttpSession session) throws Exception{
		ModelAndView mav = new ModelAndView("redirect:/");
		session.removeAttribute("user");
		return mav;
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
			if(user==null) throw new StoreException("未登录");
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
	public ModelAndView postPage(HttpServletRequest request)throws Exception{
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
	
	@RequestMapping("like-post")
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
			 int num = postService.likePost(user.getId(),Integer.valueOf(pid));
			success = true;
			message=num+"";
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
	
	@RequestMapping("detail")
	public ModelAndView postDetail(HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView("/store/postdetail");
		String pid	= request.getParameter("id");
		Map<Integer,User> userMap = new HashMap<>();
		Set<Integer> uidSet = new HashSet<>();
		Post post = postService.getPostByPrimary(Integer.valueOf(pid));
		User puser = userService.getUserByPrimaryKey(post.getUid());
		List<String> urls = commonService.listImageUrls(PostService.POST_TABLE_NAME,post.getId());
		if(uidSet.add(puser.getId())){
			userMap.put(puser.getId(),puser);
		}
		List<Comment> comments = commentService.listComment(Integer.valueOf(pid));
		for (Comment comment : comments) {
			if(comment.getCuid()!=0 && uidSet.add(comment.getCuid())){
				User user = userService.getUserByPrimaryKey(comment.getCuid());
				userMap.put(user.getId(),user);
			}
			if(comment.getSuid()!=0 && uidSet.add(comment.getSuid())){
				User user = userService.getUserByPrimaryKey(comment.getSuid());
				userMap.put(user.getId(),user);
			}
		}
		mav.addObject("post", post);
		mav.addObject("comments", comments);
		mav.addObject("userMap",userMap);
		mav.addObject("urls", urls);
		return mav;
	}
	
	@RequestMapping("post-comment")
	public void postComment(HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		boolean success = false;
		String message = null;
		try {
			User user = (User) request.getSession().getAttribute("user");
			if(user==null) throw new StoreException("未登录");
			String pid =request.getParameter("pid");
			String suid = request.getParameter("suid");//被回复人
			String content = request.getParameter("content");
			if(pid==null || "".equals(pid)){
				throw new StoreException("参数不合法");
			}
			
			if(content==null || "".equals(content)){
				throw new StoreException("内容不能为空");
			}
			
			Comment comment = new Comment();
			comment.setContent(content);
			comment.setCuid(user.getId());
			comment.setPid(Integer.valueOf(pid));
			if(suid!=null && !"".equals(suid)){
				comment.setSuid(Integer.valueOf(suid));
			}
			commentService.saveComment(comment);
			success = true;
			message="评论成功";
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
	
	@RequestMapping("personal")
	public ModelAndView personalPage(HttpSession session){
		ModelAndView mav = new ModelAndView();
		User user = (User) session.getAttribute("user");
		if(user==null){
			mav.setViewName("/store/login");
		}else{
			mav.setViewName("/store/personal");
			User user2 = userService.getUserByPrimaryKey(user.getId());
			session.setAttribute("user", user2);
			UserInfo userInfo = userService.getUserInfoByUid(user.getId());
			mav.addObject("userInfo", userInfo);
		}
		return mav;
	}
	
	@RequestMapping("save-user")
	public void saveUserInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		boolean success = false;
		String message = null;
		try {
			User user = (User) request.getSession().getAttribute("user");
			if(user==null) throw new StoreException("未登录");
			String name = request.getParameter("name");
			if(name==null || name.equals("")){
				message = "昵称不能为空";
			}else{
				user.setName(name);
				UserInfo userInfo = new UserInfo();
				userInfo.setUid(user.getId());
				userInfo.setAddress(request.getParameter("address"));
				String year = request.getParameter("collegeYear");
				if(year!=null){
					userInfo.setCollegeYear(Integer.valueOf(year));
				}
				userInfo.setSchool(request.getParameter("school"));
				userInfo.setSpecialist(request.getParameter("specialist"));
				userInfo.setTelephone(request.getParameter("telephone"));
				userService.updateUserInfo(user,userInfo);
				request.setAttribute("user",user);
				success = true;
				message = "保存成功";
			}
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
	
	@RequestMapping("save-user-image")
	public void saveUserImage(HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		boolean success = false;
		String message = null;
		try {
			User user = (User) request.getSession().getAttribute("user");
			if(user==null) throw new StoreException("未登录");
			String avatar = request.getParameter("avatar");
			if(avatar==null || avatar.equals("")) throw new StoreException("更新失败");
			userService.updateUserImage(user.getId(),Integer.valueOf(avatar));
			success = true;
			message = "保存成功";
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
	
	@RequestMapping("message")
	public ModelAndView messagePage(HttpServletRequest request){
		ModelAndView mav = new ModelAndView("/store/message");
		return mav;
	}
	
	@RequestMapping("modify-pass")
	public ModelAndView modifyPassPage(HttpSession session){
		ModelAndView mav = new ModelAndView("/store/modifypass");
		if(session.getAttribute("user")==null){
			mav.setViewName("/store/login");
		}
		return mav;
	}
	
	
	@RequestMapping("do-modify-pass")
	public void modifyPass(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		boolean success = false;
		String message = null;
		try {
			User user = (User) request.getSession().getAttribute("user");
			if(user==null) throw new StoreException("未登录");
			String oldPass = request.getParameter("oldPass");
			String newPass = request.getParameter("newPass");
			String newPass2 = request.getParameter("newPass2");
			if(null == oldPass ||oldPass.equals(user.getPassword())){
				throw new StoreException("密码不正确");
			}
			
			if(newPass==null || newPass.equals("")){
				throw new StoreException("密码不能为空");
			}
			if(!newPass.equals(newPass2)){
				throw new StoreException("密码不一致");
			}
			user.setPassword(newPass);
			userService.updatePass(user);
			request.getSession().removeAttribute("user");
			success = true;
			message = "保存成功";
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
	
	@RequestMapping("order")
	public ModelAndView orderPage(HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView("/store/order");
		User user = (User) request.getSession().getAttribute("user");
		if(user==null){
			mav.setViewName("/store/login");
		}else{
			String state = request.getParameter("state");
			String pageStr = request.getParameter("page");
			int page = 0;
			try {
				page = Integer.valueOf(pageStr);
			} catch (Exception e) {
				page=1;
			}
			if(page<1) {
				page=1; 
			}
			List<Order> orders = orderService.listOrderByState(state, user.getId(), page);
			Map<String,Long> pager = orderService.countOrderByState(state,  user.getId(), page);
			mav.addObject("orders", orders);
			mav.addObject("pager", pager);
		}
		return mav;
	}
	
	@RequestMapping("order-detail")
	public ModelAndView orderDetailPage(HttpServletRequest request,HttpSession session){
		ModelAndView mav = new ModelAndView();
		try {
			User user = (User) session.getAttribute("user");
			if(user==null) throw new StoreException("未登录");
			String idStr = request.getParameter("id");
			Map<Integer,List<OrderBook>> mapBookPos = new HashMap<>();
			Map<Integer,Book> mapBook = new HashMap<>();
			Map<Integer,Store> mapStore = new HashMap<>();
			Integer id = Integer.valueOf(idStr);
			Order order = orderService.getOrderByPrimaryKey(id);
			if(order!=null){
				mapStore.put(id,storeService.getStoreByPrimary(order.getSid()));
				List<OrderBook> bookPOs = orderService.listOrderBookByOid(id);
				for (OrderBook orderBook : bookPOs) {
					Book book = bookService.getByPrimary(orderBook.getBid());
					mapBook.put(orderBook.getBid(),book);
				}
				mapBookPos.put(id,bookPOs);
			}
			mav.setViewName("store/orderdetail");
			mav.addObject("order",order);
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
	
	@RequestMapping("my-post")
	public ModelAndView myPostPage(HttpServletRequest request) throws Exception{
		ModelAndView mav = new ModelAndView("/store/mypost");
		User user = (User) request.getSession().getAttribute("user");
		if(null==user){
			mav.setViewName("/store/login");
			return mav;
		}
		String pageStr = request.getParameter("page");
		int page = 0;
		try {
			page = Integer.valueOf(pageStr);
		} catch (Exception e) {
			page=1;
		}
		if(page<1) {
			page=1; 
		}
		List<Post> posts = postService.listPostByUser(user.getId(),page);
		Map<String,Long> pager = postService.countPostByUser(user.getId(), page);
		Map<Integer,String> statusMap = new HashMap<>();
		statusMap.put(0, "已删除");
		statusMap.put(1, "已发布");
		statusMap.put(2, "已保存");
		statusMap.put(3, "已撤回");
		
		mav.addObject("posts",posts);
		mav.addObject("pager",pager);
		mav.addObject("statusMap",statusMap);
		return mav;
	}
	
	/**
	 * 更新帖子状态
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("operation-post")
	public void updatePostStatus(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		boolean success = false;
		String message = null;
		try {
			User user = (User) request.getSession().getAttribute("user");
			if(user==null) throw new StoreException("未登录");
			String pid = request.getParameter("pid");
			String state = request.getParameter("state");
			postService.updatePostStatus(pid,state);
			success = true;
			message = "操作成功";
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
