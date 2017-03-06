package com.aring.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.aring.bean.FileInfo;
import com.aring.bean.PostTag;
import com.aring.bean.Specialist;
import com.aring.exception.StoreException;
import com.aring.service.CommonService;
import com.aring.utils.AppConfig;
import com.aring.utils.CommonUtils;

@Service
public class CommonServiceImpl implements CommonService{

	@Autowired
	private AppConfig appConfig;
	
	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	@Override
	public FileInfo saveFile(MultipartFile file) throws Exception {
		if(appConfig.getFileMaxSize()<file.getSize()){
			throw new StoreException("文件大小不能超过2M");
		}
		
		FileInfo fileInfo = new FileInfo();
		
		//文件重命名
		fileInfo.setName(makeFileName(file.getOriginalFilename()));
		
		//写入本地
		StringBuffer path = new StringBuffer();
		path.append(appConfig.getFileRoot())
			.append(appConfig.getFilePath())
			.append(fileInfo.getName());
		writeFileToDisk(path.toString(), file.getBytes());
		
		//保存文件信息
		fileInfo.setStatus(0);
		fileInfo.setType(file.getContentType());
		String url = "/"+appConfig.getFilePath()+fileInfo.getName();
		url = url.replace("\\", "/");
		fileInfo.setUrl(url);
		fileInfo.setCreateDate(new Date());
		em.persist(fileInfo);
		return fileInfo;
	}
	
	/**
	 * 文件重命名
	 * @param name
	 * @return
	 */
	private String makeFileName(String name){
		if(name == null) return null;
		StringBuffer buffer = new StringBuffer();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		String date	= format.format(new Date());
		String random = CommonUtils.strRandomer(8);
		buffer.append(date)
			.append(random)
			.append(".")
			.append(name.substring(name.lastIndexOf(".")+1, name.length()));
		return buffer.toString();
	}	
	
	private void writeFileToDisk(String path,byte[] bytes) throws StoreException{
		File file = new File(path);
		FileOutputStream fos = null;
		try {
			if(!file.getParentFile().exists()){
				file.mkdir();
			}
			if(!file.exists()){
				file.createNewFile();
			}
			fos = new FileOutputStream(file);
			fos.write(bytes);
			fos.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw new StoreException("文件保存失败");
		}finally {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
				new StoreException("文件保存失败");
			}
		}
		
		
		
	}

	@Override
	public List<String> listImageUrls(String which, int wid) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select f.url from FileInfo f where f.wid=")
			  .append(wid)
			  .append(" and f.which='").append(which)
			  .append("'");
		TypedQuery<String> query =em.createQuery(buffer.toString(),String.class);
		List<String> urls = query.getResultList();
		List<String> list = new ArrayList<>();
		for (int i = 0; i < urls.size(); i++) {
			list.add(appConfig.getFileServerDomain()+urls.get(i));
		}
		return list;
	}
	
	@Override
	public FileInfo getFileInfoByPrimatyKey(int key) {
		return em.find(FileInfo.class, key);
	}

	@Override
	public List<Specialist> listHotSpecialist(Integer n) throws Exception {
		String sql = "select s from Specialist s order by s.hot desc";
		TypedQuery<Specialist> query = em.createQuery(sql,Specialist.class);
		if(n!=null){
			query.setMaxResults(n);
		}
		return query.getResultList();
	}
	
	@Override
	public Specialist getSpecialistByPrimary(Integer id) throws Exception {
		return em.find(Specialist.class, id);
	}

	@Override
	public List<PostTag> listPostTag() throws Exception {
		String sql = "select p from PostTag p";
		TypedQuery<PostTag> query = em.createQuery(sql,PostTag.class);
		return query.getResultList();
	}
	
}