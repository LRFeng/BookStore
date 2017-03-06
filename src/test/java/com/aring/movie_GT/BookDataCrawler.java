package com.aring.movie_GT;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.aring.bean.Book;
import com.aring.bean.FileInfo;
import com.aring.exception.StoreException;
import com.aring.utils.CommonUtils;

/**
 * 教材数据爬虫
 * @author aring
 *
 */
public class BookDataCrawler {


	public static String downloadPage(String urlStr) throws Exception{
		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		InputStream is = conn.getInputStream();
		String result=null;
		InputStreamReader isr = new InputStreamReader(is,"GB2312");			
		BufferedReader br = new BufferedReader(isr);
		StringBuilder builder = new StringBuilder();
	    String line="";
		while ((line = br.readLine()) != null) {
		   builder.append(line);
		}
		result = builder.toString();
		br.close();
		isr.close();		  
	    return result;
	}
	
	public static List<String> getListData(String source){
		String p = "<div class=\"con shoplist\".*?</ul>.*?</div>";
		Pattern pattern = Pattern.compile(p);
		Matcher m = pattern.matcher(source);
		String result = null;
		while(m.find()){
			result = m.group(0);
		}
		System.out.println(result);
		
		p = "<li  ddt-pit.*?</li>";		
		pattern = Pattern.compile(p);
		m = pattern.matcher(result);
		List<String> results = new ArrayList<>();
		while(m.find()){
			results.add(m.group(0));
		}
		return results;
	}
	
	
	public static List<Book> parseXml(List<String> xmls) throws Exception{
		String p ="<img src='(.*?)' alt.*?<p class=\"name\" ><a title=\" (.*?)\".*?<p class=\"author\">.*?name='P_zz' title='(.*?)'>.*?name='P_cbs' title='(.*?)'>.*?<span class=\"price_n\">&yen;(.*?)</span>.*?<span class=\"price_r\">&yen;(.*?)</span>";
		Pattern pattern = Pattern.compile(p);
		List<Book> books = new ArrayList<>();
		int count = 0;
		for (String str : xmls) {
			Matcher m = pattern.matcher(str);
			while(m.find()){
				Book book = new Book();
				book.setMianPicUrl(m.group(1));
				book.setName(m.group(2));
				book.setAuthor(m.group(3));
				book.setPress(m.group(4));
				book.setNewPrice(Float.valueOf(m.group(5)));
				book.setOldPrice(Float.valueOf(m.group(6)));
				books.add(book);
			}
		}
		return books;
	}
	
	
	/**
	 * 文件重命名
	 * @param name
	 * @return
	 */
	private static String makeFileName(String name){
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
	
	
	public static String downloadImage(String urlStr){
		String name = makeFileName(urlStr);
		String path = "E:\\java\\fileserver\\BookStore\\images\\"+name;
		
		File file = new File(path);
		FileOutputStream fos = null;
		InputStream is = null; 
		try {
			if(!file.getParentFile().exists()){
				file.mkdir();
			}
			if(!file.exists()){
				file.createNewFile();
			}
			fos = new FileOutputStream(file);
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();
			is = conn.getInputStream();
			byte[] buffer = new byte[1024];
			while((is.read(buffer))!=-1){
				fos.write(buffer);
			}
			fos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				fos.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
				new StoreException("文件保存失败");
			}
		}
		return "/images/"+name;
		
	}
	
	
	public static void main(String[] args) {
		String url = "http://category.dangdang.com/pg1-cp01.49.01.16.00.00.html";
		try {
			String source = downloadPage(url);
			List<String> xmlData = getListData(source);
			List<Book> books = parseXml(xmlData);
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("BookStore");
			EntityManager em = emf.createEntityManager();
			EntityTransaction et = em.getTransaction();
			et.begin();
			
			for (Book book : books) {
				if(book.getMianPicUrl()==null){
					System.out.println(book);
				}
				String path = downloadImage(book.getMianPicUrl());
				book.setMianPicUrl(path);
				book.setNumber(300);
				book.setSale(20);
				book.setRemainder(280);
				book.setCreateTime(new Date());
				book.setUpdateTime(new Date());
				book.setStatus(1);
				em.persist(book);
			}
			et.commit();
			
			et.begin();
			for (Book book : books) {
				FileInfo fileInfo = new FileInfo();
				fileInfo.setCreateDate(new Date());
				String path = book.getMianPicUrl();
				path = path.replace("/images/", "");
				fileInfo.setName(path);
				fileInfo.setStatus(1);
				fileInfo.setUrl(book.getMianPicUrl());
				fileInfo.setWhich("book");
				fileInfo.setWid(book.getId());
				em.persist(fileInfo);
			}
			et.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	
}
