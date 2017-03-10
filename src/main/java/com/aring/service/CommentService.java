package com.aring.service;

import java.util.List;

import com.aring.bean.Comment;

public interface CommentService {

	public void saveComment(Comment comment) throws Exception;
	
	public List<Comment> listComment(int pid) throws Exception;
	
}
