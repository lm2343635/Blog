package org.fczm.blog.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.fczm.blog.dao.BlogDao;
import org.fczm.blog.domain.Blog;
import org.fczm.blog.domain.Type;
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
	public int getBlogsCount(String title, Type type) {
		return getHibernateTemplate().execute(new HibernateCallback<Long>() {
			public Long doInHibernate(Session session) throws HibernateException, SQLException {
				String hql="select count(*) from Blog where true=true";
				List<Object> values=new ArrayList<>();
				if(!title.equals("")&&title!=null) {
					hql+=" and title like ?";
					values.add("%"+title+"%");
				}
				if(type!=null) {
					hql+=" and type=?";
					values.add(type);
				}
				Query query=session.createQuery(hql);
				for(int i=0; i<values.size(); i++) {
					query.setParameter(i, values.get(i));
				}
				return (long)query.uniqueResult();
			}
		}).intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Blog> findByTitle(String title, Type type, int offset, int pageSize) {
		String hql="from Blog where true=true";
		List<Object> values=new ArrayList<>();
		if(!title.equals("")&&title!=null) {
			hql+=" and title like ?";
			values.add("%"+title+"%");
		}
		if(type!=null) {
			hql+=" and type=?";
			values.add(type);
		}
		hql+=" order by date desc";
		return findByPage(hql, values, offset, pageSize);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Blog> findByType(Type type) {
		String hql="from Blog where type=?";
		return getHibernateTemplate().find(hql, type);
	}

}
