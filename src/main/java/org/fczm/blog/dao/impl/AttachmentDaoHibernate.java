package org.fczm.blog.dao.impl;

import java.util.List;

import org.fczm.blog.dao.AttachmentDao;
import org.fczm.blog.domain.Attachment;
import org.fczm.blog.domain.Blog;
import org.fczm.common.hibernate3.support.PageHibernateDaoSupport;

public class AttachmentDaoHibernate extends PageHibernateDaoSupport<Attachment> implements AttachmentDao {

	public AttachmentDaoHibernate() {
		super();
		setClass(Attachment.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Attachment> findByBlog(Blog blog) {
		String hql = "from Attachment where blog = ? order by upload";
		return getHibernateTemplate().find(hql, blog);
	}
}
