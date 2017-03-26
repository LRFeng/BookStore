package com.aring.service.impl;

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

import com.aring.bean.FileInfo;
import com.aring.bean.Post;
import com.aring.exception.StoreException;
import com.aring.service.PostService;

@Service
public class PostServiceImpl implements PostService{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	@Transactional
	public void savePost(Post post) throws Exception {
		post.setCreateDate(new Date());
		post.setStatus(1);
		post.setLastCommentDate(new Date());
		em.persist(post);
	}

	@Override
	@Transactional
	public void savePostImage(int pid, String fidStr) throws Exception {
		if(fidStr==null) return;
		 
		String fids[] = fidStr.split(":");
		for (String str : fids) {
			int fid = Integer.valueOf(str);
			FileInfo fileInfo = em.find(FileInfo.class,fid);
			fileInfo.setStatus(1);
			fileInfo.setWhich(POST_TABLE_NAME);
			fileInfo.setWid(pid);
			em.merge(fileInfo);
		}
		
	}

	
	@Override
	public Post getPostByPrimary(int pid) throws Exception {
		return em.find(Post.class, pid);
	}
	
	@Override
	public List<Post> listSearchPost(Integer tag, String keyword, String sort, Integer page) throws Exception {
		if(null==keyword) keyword ="";
		StringBuffer sql = new StringBuffer();
		sql.append("select p from Post p where p.status=1 and (p.title like '%")
			.append(keyword).append("%' or p.content like '%")
			.append(keyword).append("%')");
		if(tag!=null){
			sql.append(" and p.tid=").append(tag);
		}
		String ss[] = sort.split(":");
		sql.append(" order by p.").append(ss[0]);
		if("0".equals(ss[1])){//降序
			sql.append(" desc");
		}
		System.out.println(sql.toString());
		TypedQuery<Post> query = em.createQuery(sql.toString(),Post.class);
		query.setFirstResult((page-1)*POST_PAGE_SIZE);
		query.setMaxResults(POST_PAGE_SIZE);
		return query.getResultList();
	}
	
	@Override
	public Map<String, Long> countSearchPost(Integer tag, String keyword, String sort, Integer page)
			throws Exception {
		if(null==keyword) keyword ="";
		StringBuffer sql = new StringBuffer();
		sql.append("select count(p.id) from Post p where p.status=1 and (p.title like '%")
			.append(keyword).append("%' or p.content like '%")
			.append(keyword).append("%')");
		if(tag!=null){
			sql.append(" and p.tid=").append(tag);
		}
		String ss[] = sort.split(":");
		sql.append(" order by p.").append(ss[0]);
		if("0".equals(ss[1])){//降序
			sql.append(" desc");
		}
		System.out.println(sql.toString());
		Query query = em.createQuery(sql.toString());
		Long count = (Long) query.getSingleResult();
		Map<String,Long> pager = new HashMap<>();
		pager.put("count", count);
		if(count==0){
			pager.put("countPage",0L);
			pager.put("currPage",0L);
		}else{
			pager.put("countPage",count%POST_PAGE_SIZE==0?count/POST_PAGE_SIZE:(count/POST_PAGE_SIZE+1));
			pager.put("currPage",Long.valueOf(page));
		}
		
		return pager;
		
	}

	@Override
	@Transactional
	public int likePost(Integer uid, Integer pid) throws Exception {
		Post post =	em.find(Post.class,pid);
		post.setLike(post.getLike()+1);
		em.merge(post);
		return post.getLike();
	}
	
	@Override
	public List<Post> listPostByUser(int uid, int page) throws Exception {
		String sql = "select p from Post p where p.status > 0 and p.uid="+uid+" order by p.createDate desc";
		TypedQuery<Post> query = em.createQuery(sql,Post.class);
		query.setFirstResult(POST_PAGE_SIZE*(page-1));
		return query.getResultList();
	}
	
	@Override
	public Map<String,Long> countPostByUser(int uid, int page) throws Exception {
		String sql = "select count(p.id) from Post p where p.status > 0 and p.uid="+uid;
		Query query = em.createQuery(sql.toString());
		Long count = (Long) query.getSingleResult();
		Map<String,Long> pager = new HashMap<>();
		pager.put("count", count);
		if(count==0){
			pager.put("countPage",0L);
			pager.put("currPage",0L);
		}else{
			pager.put("countPage",count%POST_PAGE_SIZE==0?count/POST_PAGE_SIZE:(count/POST_PAGE_SIZE+1));
			pager.put("currPage",Long.valueOf(page));
		}
		return pager;
	}
	
	@Transactional
	@Override
	public void updatePostStatus(String pid, String state) throws Exception {
		int status = -1;
		try {
			status = Integer.valueOf(state);
		} catch (Exception e) {
			throw new StoreException("操作不合法");
		}
		switch (status) {
		case POST_DEL:
		case POST_PUBLIC:
		case POST_RECALL:
			Post post = em.find(Post.class, pid);
			if(post!=null){
				post.setStatus(status);
				em.merge(post);
			}
			break;
		default:
			throw new StoreException("操作不合法");
		}
	}
	
}
