package org.fczm.blog.bean;

import java.util.Date;

import org.fczm.blog.domain.Type;

public class TypeBean {
	private String tid;
	private String tname;
	private Date date;
	private int count;
	
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public TypeBean(Type type) {
		super();
		this.tid = type.getTid();
		this.tname = type.getTname();
		this.date = type.getDate();
		this.count = type.getCount();
	}
	
}
