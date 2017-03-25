package com.aring.service;

import com.aring.bean.User;
import com.aring.bean.UserInfo;

public interface UserService { 
	
	/**
	 * 更新user，保存userinfo，更新fileinfo
	 * @param user
	 * @param userInfo
	 * @param fid
	 * @throws Exception
	 */
	public void register(User user,UserInfo userInfo,Integer fid) throws Exception;
	
	public void saveUser(User user) throws Exception;
	
	public void saveUserInfo(UserInfo userInfo) throws Exception;
	
	public User getUserByEmail(String email) throws Exception;
	
	public User login(String email,String password) throws Exception; 
	
	public User getUserByPrimaryKey(int key);
	
	public UserInfo getUserInfoByUid(int uid);
	
	public void updateUserInfo(User user,UserInfo userInfo) throws Exception; 
	
	public void updateUserImage(int uid,int fid) throws Exception;
	
	public void updatePass(User user) throws Exception;
	
}
