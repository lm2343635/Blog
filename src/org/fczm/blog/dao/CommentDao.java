package org.fczm.blog.dao;

import java.util.List;

import org.fczm.blog.domain.Blog;
import org.fczm.blog.domain.Comment;

public interface CommentDao {
	Comment get(String cid);
	String save(Comment comment);
	void update(Comment comment);
	void delete(Comment comment);
	
	/**
	 * 根据博客查找评论
	 * @param blog
	 * @return
	 */
	List<Comment> findByBlog(Blog blog);
}
