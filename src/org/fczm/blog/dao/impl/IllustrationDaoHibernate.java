package org.fczm.blog.dao.impl;

import java.util.List;

import org.fczm.blog.dao.IllustrationDao;
import org.fczm.blog.domain.Blog;
import org.fczm.blog.domain.Illustration;
import org.fczm.common.hibernate3.support.PageHibernateDaoSupport;

public class IllustrationDaoHibernate extends PageHibernateDaoSupport<Illustration> implements IllustrationDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Illustration> findByBlog(Blog blog) {
		String hql="from Illustration where blog=? order by upload";
		return getHibernateTemplate().find(hql, blog);
	}

}
