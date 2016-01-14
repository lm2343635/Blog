package org.fczm.blog.service;

import java.util.List;

import org.fczm.blog.bean.BlogBean;

public interface BlogManager {
	
	/**
	 * 新增博客文章
	 * @param title
	 * @param content
	 * @param date
	 * @return
	 */
	String addBlog(String title, String content, String date);
	
	/**
	 * 获取所有博客
	 * @return
	 */
	List<BlogBean> getAll();
	
	/**
	 * 根据id获取一个博客
	 * @param bid
	 * @return
	 */
	BlogBean getBlog(String bid);
	
	/**
	 * 修改博客
	 * @param bid
	 * @param title
	 * @param content
	 * @param date
	 */
	void modifyBlog(String bid, String title, String content, String date);
	
	/**
	 * 移除博客
	 * @param bid
	 */
	void removeBlog(String bid);
}
