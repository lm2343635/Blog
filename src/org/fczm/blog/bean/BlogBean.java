package org.fczm.blog.bean;

import java.util.Date;

import org.fczm.blog.domain.Blog;

public class BlogBean {
	private String bid; 
	private String title;
	private String content; 
	private Date date;
	private int readers;
	private String cover;
	private TypeBean type;
	
	public String getBid() {
		return bid;
	}

	public void setBid(String bid) {
		this.bid = bid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getReaders() {
		return readers;
	}

	public void setReaders(int readers) {
		this.readers = readers;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public TypeBean getType() {
		return type;
	}

	public void setType(TypeBean type) {
		this.type = type;
	}

	public BlogBean(Blog blog) {
		super();
		this.bid = blog.getBid();
		this.title = blog.getTitle();
		this.date = blog.getDate();
		this.readers = blog.getReaders();
		this.cover = blog.getCover();
		this.type = new TypeBean(blog.getType());
	}
}
