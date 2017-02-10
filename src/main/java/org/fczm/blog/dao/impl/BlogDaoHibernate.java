package org.fczm.blog.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.fczm.blog.dao.BlogDao;
import org.fczm.blog.domain.Blog;
import org.fczm.blog.domain.Type;
import org.fczm.common.hibernate4.support.PageHibernateDaoSupport;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

@Repository
public class BlogDaoHibernate extends PageHibernateDaoSupport<Blog> implements BlogDao {

    public BlogDaoHibernate() {
        super();
        setClass(Blog.class);
    }

    public int getBlogsCount(final String title, final Type type) {
        return getHibernateTemplate().execute(new HibernateCallback<Long>() {
            public Long doInHibernate(Session session) throws HibernateException {
                String hql = "select count(*) from Blog where true=true";
                List<Object> values = new ArrayList<Object>();
                if (!title.equals("") && title != null) {
                    hql += " and title like ?";
                    values.add("%" + title + "%");
                }
                if (type != null) {
                    hql += " and type=?";
                    values.add(type);
                }
                Query query = session.createQuery(hql);
                for (int i = 0; i < values.size(); i++) {
                    query.setParameter(i, values.get(i));
                }
                return (Long) query.uniqueResult();
            }
        }).intValue();
    }

    public List<Blog> findByTitle(String title, Type type, int offset, int pageSize) {
        String hql = "from Blog where true=true";
        List<Object> values = new ArrayList<Object>();
        if (!title.equals("") && title != null) {
            hql += " and title like ?";
            values.add("%" + title + "%");
        }
        if (type != null) {
            hql += " and type=?";
            values.add(type);
        }
        hql += " order by date desc";
        return findByPage(hql, values, offset, pageSize);
    }

    public List<Blog> findByType(Type type) {
        String hql = "from Blog where type=?";
        return (List<Blog>) getHibernateTemplate().find(hql, type);
    }

}
