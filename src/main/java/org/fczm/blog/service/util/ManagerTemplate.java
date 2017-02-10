package org.fczm.blog.service.util;

import org.directwebremoting.WebContextFactory;
import org.fczm.blog.dao.AttachmentDao;
import org.fczm.blog.dao.BlogDao;
import org.fczm.blog.dao.CommentDao;
import org.fczm.blog.dao.IllustrationDao;
import org.fczm.blog.dao.TypeDao;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import net.sf.json.JSONObject;

public class ManagerTemplate {
	
	protected BlogDao blogDao;
	protected CommentDao commentDao;
	protected TypeDao typeDao;
	protected IllustrationDao illustrationDao;
	protected AttachmentDao attachmentDao;
	
	private WebApplicationContext context = null;
	
	public BlogDao getBlogDao() {
		return blogDao;
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

	public void setBlogDao(BlogDao blogDao) {
		this.blogDao = blogDao;
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
