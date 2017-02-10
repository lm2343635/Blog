package org.fczm.blog.service.util;

import org.directwebremoting.WebContextFactory;
import org.fczm.blog.dao.AttachmentDao;
import org.fczm.blog.dao.BlogDao;
import org.fczm.blog.dao.CommentDao;
import org.fczm.blog.dao.IllustrationDao;
import org.fczm.blog.dao.TypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import net.sf.json.JSONObject;

public class ManagerTemplate {

	@Autowired
	protected BlogDao blogDao;

	@Autowired
	protected CommentDao commentDao;

	@Autowired
	protected TypeDao typeDao;

	@Autowired
	protected IllustrationDao illustrationDao;

	@Autowired
	protected AttachmentDao attachmentDao;
	
	private WebApplicationContext context = null;

	public BlogDao getBlogDao() {
		return blogDao;
	}

	public void setBlogDao(BlogDao blogDao) {
		this.blogDao = blogDao;
	}

	public CommentDao getCommentDao() {
		return commentDao;
	}

	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}

	public TypeDao getTypeDao() {
		return typeDao;
	}

	public void setTypeDao(TypeDao typeDao) {
		this.typeDao = typeDao;
	}

	public IllustrationDao getIllustrationDao() {
		return illustrationDao;
	}

	public void setIllustrationDao(IllustrationDao illustrationDao) {
		this.illustrationDao = illustrationDao;
	}

	public AttachmentDao getAttachmentDao() {
		return attachmentDao;
	}

	public void setAttachmentDao(AttachmentDao attachmentDao) {
		this.attachmentDao = attachmentDao;
	}

	public void setContext(WebApplicationContext context) {
		this.context = context;
	}

	public WebApplicationContext getContext() {
		if(context == null) {
			context = WebApplicationContextUtils.getWebApplicationContext(WebContextFactory.get().getServletContext());
		}
		return context;
	}
	
	public JSONObject getPageSizeConfig() {
		ConfigManager manager = (ConfigManager)getContext().getBean("configManager");
		return manager.getConfig().getJSONObject("pageSize");
	}

}
