package org.fczm.blog.dao;

import java.util.List;

import org.fczm.blog.domain.Attachment;
import org.fczm.blog.domain.Blog;
import org.fczm.common.hibernate3.support.CrudDao;

public interface AttachmentDao extends CrudDao<Attachment> {

	/**
	 * 根据博客查找附件
	 * @param blog
	 * @return
	 */
	List<Attachment> findByBlog(Blog blog);
}
