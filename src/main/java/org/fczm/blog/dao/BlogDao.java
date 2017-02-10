package org.fczm.blog.dao;

import java.util.List;

import org.fczm.blog.domain.Blog;
import org.fczm.blog.domain.Type;
import org.fczm.common.hibernate4.support.CrudDao;

public interface BlogDao extends CrudDao<Blog> {
	
	/**
	 * 获取指定标题的博客数量
	 * @param title
	 * @param type
	 * @return
	 */
	int getBlogsCount(String title, Type type);
	
	/**
	 * 根据标题分页查询博客
	 * @param title
	 * @param type
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	List<Blog> findByTitle(String title, Type type, int offset, int pageSize);
	
	/**
	 * 根据类型查找博客
	 * @param type
	 * @return
	 */
	List<Blog> findByType(Type type);
}
