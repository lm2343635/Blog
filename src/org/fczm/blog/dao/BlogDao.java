package org.fczm.blog.dao;

import java.util.List;

import org.fczm.blog.domain.Blog;
import org.fczm.common.hibernate3.support.CrudDao;

public interface BlogDao extends CrudDao<Blog> {
	
	/**
	 * 获取指定标题的博客数量
	 * @param title
	 * @return
	 */
	int getBlogsCount(String title);
	
	/**
	 * 根据标题分页查询博客
	 * @param title
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	List<Blog> findByTitle(String title, int offset, int pageSize);
}
