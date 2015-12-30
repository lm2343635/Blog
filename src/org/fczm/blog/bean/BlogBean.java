package org.fczm.blog.bean;

import java.util.Date;

import org.fczm.blog.domain.Blog;

public class BlogBean {
	private String bid; 
	private String title;
	private String content; 
	private Date date;
	
	public String getBid() {
		return bid;
	}
	public String getTitle() {
		return title;
	}
	public String getContent() {
		return content;
	}
	public Date getDate() {
		return date;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public BlogBean(Blog blog) {
		super();
		this.bid = blog.getBid();
		this.title = blog.getTitle();
		this.content = blog.getContent();
		this.date = blog.getDate();
	}
}
