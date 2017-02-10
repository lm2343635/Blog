package org.fczm.blog.dao.impl;

import java.util.List;

import org.fczm.blog.dao.IllustrationDao;
import org.fczm.blog.domain.Blog;
import org.fczm.blog.domain.Illustration;
import org.fczm.common.hibernate4.support.PageHibernateDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class IllustrationDaoHibernate extends PageHibernateDaoSupport<Illustration> implements IllustrationDao {

    public IllustrationDaoHibernate() {
        super();
        setClass(Illustration.class);
    }

    public List<Illustration> findByBlog(Blog blog) {
        String hql = "from Illustration where blog = ? order by upload";
        return (List<Illustration>) getHibernateTemplate().find(hql, blog);
    }

}
