package org.fczm.blog.domain;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {

	private static final long serialVersionUID = 7027641282747102086L;

	private String cid;
	private String name;
	private String content;
	private Date date;
	private Blog blog;
	
	public String getCid() {
		return cid;
	}
	public String getName() {
		return name;
	}
	public String getContent() {
		return content;
	}
	public Date getDate() {
		return date;
	}
	public Blog getBlog() {
		return blog;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public void setBlog(Blog blog) {
		this.blog = blog;
	}
	
}
