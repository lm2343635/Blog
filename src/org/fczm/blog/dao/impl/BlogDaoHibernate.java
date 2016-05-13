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

public class BlogDaoHibernate extends PageHibernateDaoSupport<Blog> implements BlogDao {

	public BlogDaoHibernate() {
		super();
		setClass(Blog.class);
	}
	
	@Override
	public int getBlogsCount(String title) {
		return getHibernateTemplate().execute(new HibernateCallback<Long>() {
			public Long doInHibernate(Session session) throws HibernateException, SQLException {
				String hql="select count(*) from Blog";
				if(!title.equals("")) {
					hql+=" where title like ?";
				}
				Query query=session.createQuery(hql);
				if(!title.equals("")) {
					query.setParameter(0, "%"+title+"%");
				}
				return (long)query.uniqueResult();
			}
		}).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Blog> findByTitle(String title, int offset, int pageSize) {
		String hql="from Blog";
		if(!title.equals("")) {
			hql+=" where title like ?";
		}
		hql+=" order by date desc";
		return !title.equals("")?  findByPage(hql, "%"+title+"%", offset, pageSize): findByPage(hql, offset, pageSize);
	}

}
