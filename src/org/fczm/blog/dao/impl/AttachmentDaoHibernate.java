package org.fczm.blog.dao.impl;

import org.fczm.blog.dao.AttachmentDao;
import org.fczm.blog.domain.Attachment;
import org.fczm.common.hibernate3.support.PageHibernateDaoSupport;

public class AttachmentDaoHibernate extends PageHibernateDaoSupport<Attachment> implements AttachmentDao {

	public AttachmentDaoHibernate() {
		super();
		setClass(Attachment.class);
	}
}
