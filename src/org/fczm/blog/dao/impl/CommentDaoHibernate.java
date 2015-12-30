package org.fczm.blog.dao.impl;

import org.fczm.blog.dao.CommentDao;
import org.fczm.blog.domain.Comment;
import org.fczm.common.hibernate3.support.PageHibernateDaoSupport;

public class CommentDaoHibernate extends PageHibernateDaoSupport implements CommentDao {

	@Override
	public Comment get(String cid) {
		return getHibernateTemplate().get(Comment.class, cid);
	}

	@Override
	public String save(Comment comment) {
		return (String)getHibernateTemplate().save(comment);
	}

	@Override
	public void update(Comment comment) {
		getHibernateTemplate().update(comment);
	}

	@Override
	public void delete(Comment comment) {
		getHibernateTemplate().delete(comment);
	}

}
