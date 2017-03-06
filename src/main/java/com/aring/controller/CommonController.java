package com.aring.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.aring.bean.FileInfo;
import com.aring.exception.StoreException;
import com.aring.service.CommonService;

/**
 * 通用控制器
 * @author aring
 *
 */
@Controller
@RequestMapping("/common")
public class CommonController {

	@Autowired
	private CommonService commonService;
	
	@RequestMapping("/single-file-upload")
	public void singleFileUpload(HttpServletResponse resp,MultipartFile file) throws Exception{
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		String result = null;
		try {
			FileInfo fileInfo =	commonService.saveFile(file);
			result = "{success:true,fid:\""+fileInfo.getId()+"\"}";
		} catch (StoreException e) {
			e.printStackTrace();
			result = "{success:false,msg:\""+e.getMessage()+"\"}";
		}catch (Exception e) {
			e.printStackTrace();
			result = "{success:false,msg:\"文件上传失败\"}";
		}finally {
			out.println(result);
			out.close();
		}
	}
	
	
	@RequestMapping("/multi-file-upload")
	public void multiFileUpload(HttpServletResponse resp,MultipartFile[] files) throws Exception{
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		String result = null;
		StringBuffer fids = new StringBuffer();
		try {
			for (MultipartFile file : files) {
				FileInfo fileInfo =	commonService.saveFile(file);
				fids.append(fileInfo.getId()).append(":");
			}
			fids.deleteCharAt(fids.length()-1);
			result = "{success:true,fids:\""+fids+"\"}";
		} catch (StoreException e) {
			e.printStackTrace();
			result = "{success:false,msg:\""+e.getMessage()+"\"}";
		}catch (Exception e) {
			e.printStackTrace();
			result = "{success:false,msg:\"文件上传失败\"}";
		}finally {
			out.println(result);
			out.close();
		}
	}
	
	
	
	
}
