package org.fczm.blog.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Blog implements Serializable {
	
	private static final long serialVersionUID = 4114418330696173690L;

	private String bid; 
	private String title;
	private String content; 
	private Date date;
	private Integer readers;
	private List<Comment> comments;
	
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
	public List<Comment> getComments() {
		return comments;
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
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public Integer getReaders() {
		return readers;
	}
	public void setReaders(Integer readers) {
		this.readers = readers;
	}
	
}
