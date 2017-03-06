package com.aring.bean;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Book {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Basic
	private String name;
	@Basic
	private String press;
	@Basic
	private String version;
	@Basic
	private String author;
	
	//海报图片
	@Basic
	@Column(name="main_pic_url")
	private String mianPicUrl;
	@Basic
	@Column(name="new_price")
	private float newPrice;
	@Basic
	@Column(name="old_price")
	private	float oldPrice;
	@Basic
	private Integer number;
	@Basic
	private Integer sale;
	@Basic
	private Integer remainder;
	@Basic
	private int status;
	@Basic
	private Date createTime;
	@Basic
	private Date updateTime;
	@Basic
	private Integer sid;
	
	
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
	public String getPress() {
		return press;
	}
	public void setPress(String press) {
		this.press = press;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public float getNewPrice() {
		return newPrice;
	}
	public void setNewPrice(float newPrice) {
		this.newPrice = newPrice;
	}
	public float getOldPrice() {
		return oldPrice;
	}
	public void setOldPrice(float oldPrice) {
		this.oldPrice = oldPrice;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Integer getSale() {
		return sale;
	}
	public void setSale(Integer sale) {
		this.sale = sale;
	}
	public Integer getRemainder() {
		return remainder;
	}
	public void setRemainder(Integer remainder) {
		this.remainder = remainder;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getMianPicUrl() {
		return mianPicUrl;
	}
	public void setMianPicUrl(String mianPicUrl) {
		this.mianPicUrl = mianPicUrl;
	}
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", press=" + press + ", version=" + version + ", author=" + author
				+ ", mianPicUrl=" + mianPicUrl + ", newPrice=" + newPrice + ", oldPrice=" + oldPrice + ", number="
				+ number + ", sale=" + sale + ", remainder=" + remainder + ", status=" + status + ", createTime="
				+ createTime + ", updateTime=" + updateTime + ", sid=" + sid + "]";
	}
	
	
	
}
