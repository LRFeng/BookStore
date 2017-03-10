package com.aring.service.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aring.bean.Comment;
import com.aring.bean.Post;
import com.aring.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
	
	@PersistenceContext
	private EntityManager em;

	@Transactional
	@Override
	public void saveComment(Comment comment) throws Exception {
		comment.setCreateDate(new Date());
		comment.setStatus(1);
		int	pid = comment.getPid();
		Post post = em.find(Post.class, pid);
		post.setCommentNum(post.getCommentNum()+1);
		post.setLastCommentDate(new Date());
		em.persist(comment);
		em.merge(post);
	}

	@Override
	public List<Comment> listComment(int pid) throws Exception {
		String sql = "select c from Comment c where c.pid="+pid
				+" and c.status=1 order by c.createDate desc";
		TypedQuery<Comment> query = em.createQuery(sql, Comment.class);
		return query.getResultList();
	}
	
	
	
	
	
}
