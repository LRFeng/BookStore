package com.aring.management.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.stereotype.Service;

import com.aring.management.bean.Menu;
import com.aring.management.bean.MenuBasic;

@Service("menuService")
public class MenuService {
	
	@PersistenceContext
	private EntityManager em;
	
	 public List<Menu> queryMenus(){
		return createMenu(0);
	 }
	 
	 public List<MenuBasic> listMemu(){
		 String sql = "select m from MenuBasic m";
		 TypedQuery<MenuBasic> query = em.createQuery(sql, MenuBasic.class);
		 List<MenuBasic> menuBasics = query.getResultList();
		 return menuBasics;
	 }
	 
	 /**
	  * 递归创建菜单
	  * @param pid 上级ID，第一层为0
	  * @return
	  */
	 private List<Menu> createMenu(int pid){
		 List<Menu> menus = new ArrayList<>();
		 String sql = "select m from MenuBasic m where m.status=1 and m.parentId="+pid;
		 TypedQuery<MenuBasic> query = em.createQuery(sql, MenuBasic.class);
		 List<MenuBasic> menuBasics = query.getResultList();
		 if(menuBasics == null || menuBasics.size()==0){
			 return null;
		 }
		 for (MenuBasic menuBasic : menuBasics) {
			Menu menu = new Menu(menuBasic.getId(),menuBasic.getName());
			if(menuBasic.getIsFile()==1){
				Map<String, Object> map = new HashMap<>();
				map.put("url",menuBasic.getUrl());
				menu.setAttributes(map);
			}else{ //文件夹递归调用
				List<Menu> children = createMenu(menuBasic.getId());
				menu.setChildren(children);
			}
			menus.add(menu);
		}
		return menus;
	 }
	
	
}
