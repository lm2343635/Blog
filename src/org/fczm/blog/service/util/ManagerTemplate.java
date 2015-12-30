package org.fczm.blog.service.util;

import org.fczm.blog.dao.BlogDao;
import org.fczm.blog.dao.CommentDao;

public class ManagerTemplate {
	protected BlogDao blogDao;
	protected CommentDao commentDao;
	
	public BlogDao getBlogDao() {
		return blogDao;
	}
	public CommentDao getCommentDao() {
		return commentDao;
	}
	public void setBlogDao(BlogDao blogDao) {
		this.blogDao = blogDao;
	}
	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}
}
