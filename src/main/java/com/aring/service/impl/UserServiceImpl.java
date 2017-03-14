package com.aring.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aring.bean.FileInfo;
import com.aring.bean.User;
import com.aring.bean.UserInfo;
import com.aring.exception.StoreException;
import com.aring.service.UserService;
import com.aring.utils.AppConfig;

@Service
public class UserServiceImpl implements UserService{

	final private static String USER_TABLE_NAME="user";
	
	@Autowired
	private AppConfig appConfig;
	
	@PersistenceContext
    protected EntityManager em;
	
	@Transactional
	@Override
	public void register(User user,UserInfo userInfo,Integer fid) throws Exception {
		FileInfo fileInfo = null;
		//获取图片信息
		if(fid!=null){
			fileInfo = em.find(FileInfo.class, fid);
			if(fileInfo!=null){
				user.setAvatar(fileInfo.getUrl());
			}
		}
		user.setStatus(1);
		em.merge(user);
		if(fileInfo!=null){
			fileInfo.setWhich(USER_TABLE_NAME);
			fileInfo.setWid(user.getId());
			fileInfo.setStatus(1);
			em.merge(fileInfo);
		}
		em.persist(userInfo);
	}
	
	
	@Override
	@Transactional
	public void saveUser(User user) throws Exception {
		User user2	= getUserByEmail(user.getEmail());
		if(user2!=null) throw new StoreException("邮箱地址已被注册"); 
		em.persist(user);
	}

	@Override
	@Transactional
	public void saveUserInfo(UserInfo userInfo) throws Exception {
		em.persist(userInfo);
	}

	@Override
	public User getUserByEmail(String email) throws Exception {
		String sql = "select u from User u where u.email = '"+email+"'";
		List list = em.createQuery(sql).getResultList();
		if(list!=null && list.size()!=0) return (User) list.get(0);
		else return null;
	}

	@Override
	public User login(String email,String password) throws Exception {
		User user = getUserByEmail(email);
		if(user==null) throw new StoreException("账号未注册");
		if(!user.getPassword().equals(password)) throw new StoreException("密码不正确");
		user.setAvatar(appConfig.getFileServerDomain()+user.getAvatar());
		return user;
	}


	@Override
	public User getUserByPrimaryKey(int key) {
		User user = em.find(User.class, key);
		user.setAvatar(appConfig.getFileServerDomain()+user.getAvatar());
		return user;
	}
	
	@Override
	public UserInfo getUserInfoByUid(int uid) {
		String sql = "select u from UserInfo u where u.uid="+uid;
		TypedQuery<UserInfo> query = em.createQuery(sql,UserInfo.class);
		return query.getSingleResult();
	}
	
	@Override
	@Transactional
	public void updateUserInfo(User user,UserInfo userInfo) throws Exception{
		UserInfo userInfo2 = getUserInfoByUid(userInfo.getUid());
		userInfo.setId(userInfo2.getId());
		User user2 = em.find(User.class,user.getId());
		user2.setName(user.getName());
		em.merge(user2);
		em.merge(userInfo);
	}
	
	@Override
	@Transactional
	public void updateUserImage(int uid,int fid) throws Exception {
		FileInfo fileInfo = em.find(FileInfo.class, fid);
		fileInfo.setWhich(USER_TABLE_NAME);
		fileInfo.setWid(uid);
		em.merge(fileInfo);
		User user  = em.find(User.class, uid);
		user.setAvatar(fileInfo.getUrl());
		em.merge(user);
	}
	

}
