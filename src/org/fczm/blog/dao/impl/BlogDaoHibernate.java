package org.fczm.blog.dao.impl;

import java.util.List;

import org.fczm.blog.dao.BlogDao;
import org.fczm.blog.domain.Blog;
import org.fczm.common.hibernate3.support.PageHibernateDaoSupport;

public class BlogDaoHibernate extends PageHibernateDaoSupport implements BlogDao {

	@Override
	public Blog get(String bid) {
		return getHibernateTemplate().get(Blog.class, bid);
	}

	@Override
	public String save(Blog blog) {
		return (String)getHibernateTemplate().save(blog);
	}

	@Override
	public void update(Blog blog) {
		getHibernateTemplate().update(blog);
	}

	@Override
	public void delete(Blog blog) {
		getHibernateTemplate().delete(blog);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Blog> findAll() {
		return getHibernateTemplate().find("from Blog order by date desc");
	}

}
