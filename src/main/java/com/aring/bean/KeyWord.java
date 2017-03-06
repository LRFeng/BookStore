package com.aring.bean;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 搜索关键词
 * @author aring
 *
 */
@Entity
public class KeyWord {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Basic
	@Column(name="kew_word",unique=true)
	private String keyWord;
	
	/*
	 * 统一关键词教材的ID，
	 * 格式：7/8/9/6/5
	 */
	@Basic
	private String bids;
	
	/*
	 * 使用热度
	 */
	@Basic
	private int hot;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public String getBids() {
		return bids;
	}

	public void setBids(String bids) {
		this.bids = bids;
	}

	public int getHot() {
		return hot;
	}

	public void setHot(int hot) {
		this.hot = hot;
	}
	
	
	
	
}
