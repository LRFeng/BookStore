package com.aring.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.aring.bean.FileInfo;
import com.aring.bean.PostTag;
import com.aring.bean.Specialist;

public interface CommonService {
	
	/**
	 * 热门专业大小
	 */
	final public static int HOT_SPECIALIST_SIZE = 30; 
	
	public FileInfo saveFile(MultipartFile file) throws Exception;
	
	/**
	 * 获取表对象的图片路径
	 * @param which	表名
	 * @param wid 对象ID
	 * @return
	 */
	public List<String> listImageUrls(String which,int wid);
	
	public FileInfo getFileInfoByPrimatyKey(int key);
	
	
	/**
	 * 获取热门专业
	 * @param n 前n个专业，w为null时，表示获取全部
	 * @return
	 * @throws Exception
	 */
	public List<Specialist> listHotSpecialist(Integer n) throws Exception;
	
	public Specialist getSpecialistByPrimary(Integer id) throws Exception;
	
	public List<PostTag> listPostTag() throws Exception;
	
	
}
