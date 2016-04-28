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
	 * 根据id获取一个博客处正文以外的基本信息
	 * @param bid
	 * @param reader 是否为读者
	 * @return
	 */
	BlogBean getBlogInfo(String bid, boolean reader);
	
	/**
	 * 获取博客正文
	 * @param bid
	 * @return
	 */
	String getBlogContent(String bid);
	
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
	
	/**
	 * 根据标题获取博客数量
	 * @param title
	 * @return
	 */
	int getBlogsCount(String title);
	
	/**
	 * 查询博客
	 * @param title 标题
	 * @param page 页码
	 * @param pageSize 页面大小
	 * @return
	 */
	List<BlogBean> searchBlogs(String title, int page, int pageSize);
	
	/**
	 * 重新生成所有博客
	 */
	void regenerate();
}
