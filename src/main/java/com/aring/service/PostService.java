package com.aring.service;

import java.util.List;
import java.util.Map;

import com.aring.bean.Post;

public interface PostService {
	/**
	 * 删除状态
	 */
	public final static int POST_DEL = 0;
	/**
	 * 发布状态
	 */
	public final static int POST_PUBLIC = 1;
	/**
	 * 保存状态，草稿
	 */
	public final static int POST_SAVE = 2;
	/**
	 * 撤回状态
	 */
	public final static int POST_RECALL = 3;
	
	
	final public static String POST_TABLE_NAME="post";
	
	final public static int POST_PAGE_SIZE =10;
	
	public void savePost(Post post) throws Exception; 
	
	public void savePostImage(int pid,String fids) throws Exception;
	
	public Post getPostByPrimary(int pid) throws Exception;
 	
	/**
	 * 条件查询帖子
	 * @param tag
	 * @param keyword
	 * @param sort
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<Post> listSearchPost(Integer tag,String keyword,String sort,Integer page) throws Exception;
	
	public Map<String,Long> countSearchPost(Integer tag,String keyword,String sort,Integer page) throws Exception;
	
	/**
	 * 点赞
	 * @param uid
	 * @param pid
	 * @throws Exception
	 */
	public int likePost(Integer uid,Integer pid)throws Exception;
	
	/**
	 * 
	 * @param uid
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<Post> listPostByUser(int uid,int page) throws Exception;
	
	public Map<String, Long> countPostByUser(int uid,int page) throws Exception;
	
	public void updatePostStatus(String pid,String status) throws Exception;
	
	
}
