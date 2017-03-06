package com.aring.bean;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Order {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Basic
	private Integer uid;
	@Basic
	private Integer sid;
	@Basic
	@Column(name="user_message")
	private String userMessage;
	@Basic
	@Column(name="store_message")
	private String storeMessage;
	
	//自提开始时间
	@Basic
	@Column(name="extract_start_date")
	private Date extractStartDate;
	
	//自提结束时间
	@Basic
	@Column(name="extract_end_date")
	private Date extractEndDate;
	
	@Basic
	private double sum;
	@Basic
	private Integer count;
	@Basic
	@Column(name="create_date")
	private Date createDate;
	@Basic
	private int status;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public String getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

	public String getStoreMessage() {
		return storeMessage;
	}

	public void setStoreMessage(String storeMessage) {
		this.storeMessage = storeMessage;
	}

	public Date getExtractStartDate() {
		return extractStartDate;
	}

	public void setExtractStartDate(Date extractStartDate) {
		this.extractStartDate = extractStartDate;
	}

	public Date getExtractEndDate() {
		return extractEndDate;
	}

	public void setExtractEndDate(Date extractEndDate) {
		this.extractEndDate = extractEndDate;
	}

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", uid=" + uid + ", sid=" + sid + ", userMessage=" + userMessage + ", storeMessage="
				+ storeMessage + ", extractStartDate=" + extractStartDate + ", extractEndDate=" + extractEndDate
				+ ", sum=" + sum + ", count=" + count + ", createDate=" + createDate + ", status=" + status + "]";
	}
	
}
