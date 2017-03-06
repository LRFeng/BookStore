package com.aring.bean;

public class BookPO{
	private Integer id;
	private double price;
	private int num;
	private boolean check;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public boolean isCheck() {
		return check;
	}
	public void setCheck(boolean check) {
		this.check = check;
	}
	@Override
	public String toString() {
		return "Book [id=" + id + ", price=" + price + ", num=" + num + ", check=" + check + "]";
	}
}