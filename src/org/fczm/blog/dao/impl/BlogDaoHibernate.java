package org.fczm.blog.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.fczm.blog.dao.BlogDao;
import org.fczm.blog.domain.Blog;
import org.fczm.common.hibernate3.support.PageHibernateDaoSupport;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

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

	@Override
	public int getBlogsCount(String title) {
		String hql="select count(*) from Blog where title like ?";
		return getHibernateTemplate().execute(new HibernateCallback<Long>() {
			public Long doInHibernate(Session session) throws HibernateException, SQLException {
				Query query=session.createQuery(hql);
				query.setParameter(0, "%"+title+"%");
				return (long)query.uniqueResult();
			}
		}).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Blog> findByTitle(String title, int offset, int pageSize) {
		String hql="from Blog where title like ?";
		return findByPage(hql, "%"+title+"%", offset, pageSize);
	}

}
