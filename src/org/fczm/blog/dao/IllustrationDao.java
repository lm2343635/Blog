package org.fczm.blog.dao;

import java.util.List;

import org.fczm.blog.domain.Blog;
import org.fczm.blog.domain.Illustration;
import org.fczm.common.hibernate3.support.CrudDao;

public interface IllustrationDao extends CrudDao<Illustration> {

	/**
	 * 根据博文查找插图
	 * @param blog
	 * @return
	 */
	List<Illustration> findByBlog(Blog blog);
}
