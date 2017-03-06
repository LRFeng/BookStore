package com.aring.bean;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OrderBook {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Basic
	private Integer number;
	
	@Basic
	private double price;
	
	@Basic
	private double sum;
	
	@Basic
	private Integer oid;
	
	@Basic
	private Integer bid;
	
	@Basic
	@Column(name="carte_date")
	private Date creatDate;
	
	@Basic
	private int status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	public Integer getOid() {
		return oid;
	}
	
	public void setOid(Integer oid) {
		this.oid = oid;
	}
	
	public Integer getBid() {
		return bid;
	}
	
	public void setBid(Integer bid) {
		this.bid = bid;
	}
	
	public Date getCreatDate() {
		return creatDate;
	}

	public void setCreatDate(Date creatDate) {
		this.creatDate = creatDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "OrderBook [id=" + id + ", number=" + number + ", price=" + price + ", sum=" + sum + ", oid=" + oid
				+ ", bid=" + bid + ", creatDate=" + creatDate + ", status=" + status + "]";
	}

}
