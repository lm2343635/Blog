package org.fczm.blog.domain;

import java.io.Serializable;
import java.util.Date;

public class Attachment implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String [] units = {"B", "KB", "MB", "GB", "TB"};
	
	private String aid;
	private String store;
	private String filename;
	private Long size;
	private Date upload;
	private Blog blog;
	
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
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
	
	public String getSizeString() {
	    long size = this.size;
	    int unit = 0;
	    while(size >= 1024) {
	        size /= 1024;
	        unit ++;
	    }
	    return size + " " + units[unit];
	}

}
