package com.aring.service;

import java.util.List;
import java.util.Map;

import com.aring.bean.Post;

public interface PostService {
	
	final public static String POST_TABLE_NAME="post";
	
	final public static int POST_PAGE_SIZE =10;
	
	public void savePost(Post post) throws Exception; 
	
	public void savePostImage(int pid,String fids) throws Exception;
	
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
	public void likePost(Integer uid,Integer pid)throws Exception;
	
	
}
