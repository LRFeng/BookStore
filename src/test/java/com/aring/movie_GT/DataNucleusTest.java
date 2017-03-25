package com.aring.movie_GT;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.Test;

import com.aring.bean.BookSpecialist;
import com.aring.bean.Order;
import com.aring.bean.OrderBook;
import com.aring.bean.PostTag;
import com.aring.bean.Store;
import com.aring.management.bean.MenuBasic;

public class DataNucleusTest {

	public void testUserBasic(){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("BookStore");
		EntityManager manager = factory.createEntityManager();
		EntityTransaction et = manager.getTransaction();
		try {
			/*et.begin();
			User user = new User();
			user.setEmail("aringlai@163.com");
			user.setPassword("123456");
			user.setLoginTime(new Date());
			manager.persist(user);
			et.commit();
			et.begin();
			UserInfo userInfo = new UserInfo();
			userInfo.setCollegeYear(2013);
			userInfo.setUid(user.getId());
			userInfo.setSchool("广东工业大学");
			userInfo.setSpecialist("计算机科学与技术");
			manager.persist(userInfo);
			et.commit();
			System.out.println(user);
			System.out.println(userInfo);*/
			
			
		} catch (Exception e) {
			e.printStackTrace();
			et.rollback();
		}finally {
			if(et.isActive()){
				manager.close();
			}
		}
		
	}
	
	public void testBook(){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("BookStore");
		EntityManager manager = factory.createEntityManager();
		EntityTransaction et = manager.getTransaction();
		try {
			 et.begin();
			 BookSpecialist bookSpecialist = new BookSpecialist();
			 bookSpecialist.setBid(1);
			 bookSpecialist.setSpid(27);
			 manager.persist(bookSpecialist);
			 et.commit();
			/*Query query = manager.createQuery(sql);
			 List list = query.getResultList();
			 System.out.println(list.size());*/
			/*Book book =	manager.find(Book.class, 1);
			System.out.println(book);*/
		} catch (Exception e) {
			e.printStackTrace();
			et.rollback();
		}finally {
			if(et.isActive()){
				manager.close();
			}
		}
	}
	
	public void specialistTest(){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("BookStore");
		EntityManager manager = factory.createEntityManager();
		EntityTransaction et = manager.getTransaction();
		try {
			String s = "工业工程/测控技术与仪器/机械电子工程/车辆工程/包装工程/自动/电气工程及其自动化/物联网工程/电子信息科学与技术/化学工程与工艺/应用化学"
		+"/制药工程/食品科学与工程/生物工程/集成电路设计与集成系统/电子信息工程/通信工程/信息工程/土木工程/道路桥梁与渡河工程/城市地下空间工程/给排水科学与工程"
		+"/建筑环境与能源应用工程/工程管理/测绘工程/交通运输/计算机科学与技术/网络工程/软件工程/信息安全/材料成型及控制工程/金属材料工程/高分子材料与工程/能源与动力工程/微电子科学与工程/新能源材料与器件/安全工程/环境工程";
			String ss[] = s.split("/");
			et.begin();
			/*for (String str : ss) {
				Specialist specialist = new Specialist();
				specialist.setCreateDate(new Date());
				specialist.setStatus(1);
				specialist.setHot(0);
				specialist.setName(str);
				manager.persist(specialist);
			}
			et.commit();*/
		} catch (Exception e) {
			e.printStackTrace();
			et.rollback();
		}finally {
			if(et.isActive()){
				manager.close();
			}
		}
	}
	
	public void orderTest(){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("BookStore");
		EntityManager manager = factory.createEntityManager();
		EntityTransaction et = manager.getTransaction();
		try {
			et.begin();
			Order order = new Order();
			manager.persist(order);
			et.commit();
			
			et.begin();
			OrderBook orderBook = new OrderBook();
			orderBook.setOid(order.getId());
			manager.persist(orderBook);
			et.commit();
		} catch (Exception e) {
			e.printStackTrace();
			et.rollback();
		}finally {
			if(et.isActive()){
				manager.close();
			}
		}
	}
	
	public void storeTest(){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("BookStore");
		EntityManager manager = factory.createEntityManager();
		EntityTransaction et = manager.getTransaction();
		try {
			et.begin();
			Store store = new Store();
			manager.persist(store);
			et.commit();
		} catch (Exception e) {
			e.printStackTrace();
			et.rollback();
		}finally {
			if(et.isActive()){
				manager.close();
			}
		}
	}
	
	public void postTagTest(){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("BookStore");
		EntityManager manager = factory.createEntityManager();
		EntityTransaction et = manager.getTransaction();
		try {
			et.begin();
			PostTag tag = new PostTag();
			tag.setCreateDate(new Date());
			tag.setName("吐槽帖");
			tag.setStatus(1);
			manager.persist(tag);
			et.commit();
		} catch (Exception e) {
			e.printStackTrace();
			et.rollback();
		}finally {
			if(et.isActive()){
				manager.close();
			}
		}
	}
	
	@Test
	public void testMemu(){
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("BookStore");
		EntityManager manager = factory.createEntityManager();
		EntityTransaction et = manager.getTransaction();
		try {
			et.begin();
			MenuBasic menuBasic = new MenuBasic();
			menuBasic.setIsFile(0);
			menuBasic.setName("店铺管理");
			menuBasic.setStatus(1);
			manager.persist(menuBasic);
			et.commit();
		} catch (Exception e) {
			e.printStackTrace();
			et.rollback();
		}finally {
			if(et.isActive()){
				manager.close();
			}
		}
	}
	
}
