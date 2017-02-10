package org.fczm.blog.domain;

import java.io.Serializable;
import java.util.Date;

public class Illustration implements Serializable {
	private static final long serialVersionUID = 2675496364643444273L;
	
	private String iid;
	private String filename;
	private Date upload;
	private Blog blog;
	
	public String getIid() {
		return iid;
	}
	public void setIid(String iid) {
		this.iid = iid;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public Date getUpload() {
		return upload;
	}
	public void setUpload(Date upload) {
		this.upload = upload;
	}
	public Blog getBlog() {
		return blog;
	}
	public void setBlog(Blog blog) {
		this.blog = blog;
	}

}
