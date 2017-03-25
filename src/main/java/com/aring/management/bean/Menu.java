package com.aring.management.bean;

import java.util.List;
import java.util.Map;

/**
 * easyui菜单
 * @author aring
 *
 */
public class Menu {
	
	private	int id;
	
	private String text;
	
	private State state;
	
	private List<Menu> children;
	
	private Map<String,Object> attributes;
	
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	
	public Menu() {
		this.state=State.open;
	}
	
	public Menu(String text) {
		this();
		this.text = text;
	}
	
	public Menu(int id,String text) {
		this(text);
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}
	
	enum State{
		open("open"),
		closed("closed");
		
		private String value; 
		
		private State(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
	}	
}