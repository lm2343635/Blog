package org.fczm.blog.bean;

import java.util.Date;

import org.fczm.blog.domain.Illustration;

public class IllustrationBean {
	private String iid;
	private String filename;
	private Date upload;
	private String bid;
	
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
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	
	public IllustrationBean(Illustration illustration) {
		super();
		this.iid = illustration.getIid();
		this.filename = illustration.getFilename();
		this.upload = illustration.getUpload();
		this.bid = illustration.getBlog().getBid();
	}
	
}
