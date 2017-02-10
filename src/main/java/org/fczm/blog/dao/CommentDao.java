package org.fczm.blog.dao;

import java.util.List;

import org.fczm.blog.domain.Blog;
import org.fczm.blog.domain.Comment;
import org.fczm.common.hibernate4.support.CrudDao;

public interface CommentDao extends CrudDao<Comment> {
	
	/**
	 * 根据博客查找评论
	 * @param blog
	 * @return
	 */
	List<Comment> findByBlog(Blog blog);
}
