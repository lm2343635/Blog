package org.fczm.blog.dao.impl;

import java.util.List;

import org.fczm.blog.dao.CommentDao;
import org.fczm.blog.domain.Blog;
import org.fczm.blog.domain.Comment;
import org.fczm.common.hibernate4.support.PageHibernateDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class CommentDaoHibernate extends PageHibernateDaoSupport<Comment> implements CommentDao {

    public CommentDaoHibernate() {
        super();
        setClass(Comment.class);
    }

    public List<Comment> findByBlog(Blog blog) {
        return (List<Comment>) getHibernateTemplate().find("from Comment where blog = ? order by date desc", blog);
    }

}
