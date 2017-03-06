package com.aring.bean;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 店铺信息
 * @author aring
 *
 */
@Entity
public class Store {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Basic
	private String name;
	@Basic
	private String address;
	@Basic
	private String telephone;
	@Basic
	@Column(name="pic_url")
	private String picUrl;
	@Basic
	@Column(name="book_sum")
	private Integer bookSum;
	@Basic
	@Column(name="book_sale")
	private Integer bookSale;
	@Basic
	@Column(name="create_date")
	private Date createDate;

	@Basic
	@Column(name="update_date")
	private Date updateDate;
	@Basic
	private int status;
	
	//店铺拥有者
	@Basic
	private int	suid;
	
	//店铺审批者
	@Basic
	private int guid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public Integer getBookSum() {
		return bookSum;
	}

	public void setBookSum(Integer bookSum) {
		this.bookSum = bookSum;
	}

	public Integer getBookSale() {
		return bookSale;
	}

	public void setBookSale(Integer bookSale) {
		this.bookSale = bookSale;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getSuid() {
		return suid;
	}

	public void setSuid(int suid) {
		this.suid = suid;
	}

	public int getGuid() {
		return guid;
	}

	public void setGuid(int guid) {
		this.guid = guid;
	}
}
