package com.aring.bean;

import java.util.List;

/**
 * 购书车
 * @author aring
 *
 */
public class Cart {
	
	private List<BookPO> books;
	private double sum;
	private int count;
	
	public List<BookPO> getBooks() {
		return books;
	}


	public void setBooks(List<BookPO> books) {
		this.books = books;
	}

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}


	@Override
	public String toString() {
		return "Cart [books=" + books + ", sum=" + sum + ", count=" + count + "]";
	}
	
	
}