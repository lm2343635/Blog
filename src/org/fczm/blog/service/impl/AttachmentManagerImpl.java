package org.fczm.blog.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;
import org.fczm.blog.bean.AttachmentBean;
import org.fczm.blog.domain.Attachment;
import org.fczm.blog.domain.Blog;
import org.fczm.blog.service.AttachmentManager;
import org.fczm.blog.service.util.ManagerTemplate;
import org.fczm.blog.servlet.UploadServlet;
import org.fczm.common.util.RandomValidateCode;

public class AttachmentManagerImpl extends ManagerTemplate implements AttachmentManager {
	
	@Override
	public AttachmentBean getAttachment(String aid) {
		Attachment attachment = attachmentDao.get(aid);
		if(attachment == null) {
			return null;
		}
		return new AttachmentBean(attachment);
	}

	@Override
	public List<AttachmentBean> getAttachmentsByBid(String bid) {
		List<AttachmentBean> attachments = new ArrayList<>();
		Blog blog = blogDao.get(bid);
		if(blog == null) {
			return null;
		}
		for(Attachment attachment: attachmentDao.findByBlog(blog)) {
			attachments.add(new AttachmentBean(attachment));
		}
		return attachments;
	}

	@Override
	public boolean removeAttachment(String aid) {
		Attachment attachment = attachmentDao.get(aid);
		if(attachment == null) {
			return false;
		}
		String rootPath = WebContextFactory.get().getServletContext().getRealPath("/");
		String filePath = rootPath + File.separator + UploadServlet.UPLOAD_FOLDER + File.separator
				+ attachment.getBlog().getBid() + File.separator + attachment.getFilename();
		File file = new File(filePath);
		if(file.delete() || !file.exists()) {
			attachmentDao.delete(attachment);
			return true;
		}
		return true;
	}

	@Override
	public String validateDownload(String aid, String code, HttpSession session) {
		Attachment attachment = attachmentDao.get(aid);
		if(attachment == null) {
			return null;
		}
		if(!code.equalsIgnoreCase((String)session.getAttribute(RandomValidateCode.RANDOMCODEKEY))) {
			return null;
		}
		Map<String, String> token = new HashMap<>();
		token.put("aid", aid);
		token.put("token", UUID.randomUUID().toString());
		session.setAttribute(DOWNLOAD_TOKEN, token);
		return token.get("token");
	}

}
