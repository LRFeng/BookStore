package com.aring.bean;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
/**
 * 文件信息
 * @author aring
 *
 */
@Entity
public class FileInfo {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Basic
	private String name;

	@Basic
	private String url;
	@Basic
	private String type;
	
	/*
	 * 外键数据表名称
	 */
	@Basic
	private String which;
	
	/*
	 * 外键id
	 */
	@Basic
	private Integer wid;
	
	@Basic
	private Integer	status;
	
	@Basic
	@Column(name="create_date")
	private Date createDate;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getWhich() {
		return which;
	}

	public void setWhich(String which) {
		this.which = which;
	}

	public Integer getWid() {
		return wid;
	}

	public void setWid(Integer wid) {
		this.wid = wid;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "FileInfo [id=" + id + ", name=" + name + ", url=" + url + ", type=" + type + ", which=" + which
				+ ", wid=" + wid + ", status=" + status + ", createDate=" + createDate + "]";
	}
	
	
}
