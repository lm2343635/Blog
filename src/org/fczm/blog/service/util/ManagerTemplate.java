package org.fczm.blog.service.util;

import org.fczm.blog.dao.BlogDao;
import org.fczm.blog.dao.CommentDao;
import org.fczm.blog.dao.IllustrationDao;
import org.fczm.blog.dao.TypeDao;

public class ManagerTemplate {
	protected BlogDao blogDao;
	protected CommentDao commentDao;
	protected TypeDao typeDao;
	protected IllustrationDao illustrationDao;
	
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
	public TypeDao getTypeDao() {
		return typeDao;
	}
	public void setTypeDao(TypeDao typeDao) {
		this.typeDao = typeDao;
	}
	public IllustrationDao getIllustrationDao() {
		return illustrationDao;
	}
	public void setIllustrationDao(IllustrationDao illustrationDao) {
		this.illustrationDao = illustrationDao;
	}
}
