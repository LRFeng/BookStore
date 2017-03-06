package com.aring.bean;

import javax.persistence.Basic;

/**
 * 管理员信息
 * @author aring
 *
 */
public class ManagerInfo{

	@Basic
	private String telephone;
	
	@Basic
	private String address;
	
	@Basic
	private String sex;
	
	@Basic
	private int age;
	
	@Basic
	private String idNumber;
	
	@Basic
	private Integer uid;

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}
}
