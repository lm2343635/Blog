package org.fczm.blog.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Type implements Serializable {
	private static final long serialVersionUID = -3342121880319065L;
	
	private String tid;
	private String tname;
	private Date date;
	private Integer count;
	private List<Blog> blogs;
	
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
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public List<Blog> getBlogs() {
		return blogs;
	}
	public void setBlogs(List<Blog> blogs) {
		this.blogs = blogs;
	}

}
