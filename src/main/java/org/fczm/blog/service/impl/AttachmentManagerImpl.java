package org.fczm.blog.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteProxy;
import org.fczm.blog.bean.AttachmentBean;
import org.fczm.blog.domain.Attachment;
import org.fczm.blog.domain.Blog;
import org.fczm.blog.service.AttachmentManager;
import org.fczm.blog.service.util.ManagerTemplate;
import org.fczm.common.util.RandomValidateCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RemoteProxy(name = "AttachmentManager")
public class AttachmentManagerImpl extends ManagerTemplate implements AttachmentManager {

    public AttachmentBean getAttachment(String aid) {
        Attachment attachment = attachmentDao.get(aid);
        if (attachment == null) {
            return null;
        }
        return new AttachmentBean(attachment);
    }

    public List<AttachmentBean> getAttachmentsByBid(String bid) {
        List<AttachmentBean> attachments = new ArrayList<AttachmentBean>();
        Blog blog = blogDao.get(bid);
        if (blog == null) {
            return null;
        }
        for (Attachment attachment : attachmentDao.findByBlog(blog)) {
            attachments.add(new AttachmentBean(attachment));
        }
        return attachments;
    }

    @Transactional
    public boolean removeAttachment(String aid) {
        Attachment attachment = attachmentDao.get(aid);
        if (attachment == null) {
            return false;
        }
        String filePath = configComponent.rootPath + File.separator + configComponent.UploadFolder + File.separator
                + attachment.getBlog().getBid() + File.separator + attachment.getFilename();
        File file = new File(filePath);
        if (file.delete() || !file.exists()) {
            attachmentDao.delete(attachment);
            return true;
        }
        return true;
    }

    public String validateDownload(String aid, String code, HttpSession session) {
        Attachment attachment = attachmentDao.get(aid);
        if (attachment == null) {
            return null;
        }
        if (!code.equalsIgnoreCase((String) session.getAttribute(RandomValidateCode.RANDOMCODEKEY))) {
            return null;
        }
        Map<String, String> token = new HashMap<String, String>();
        token.put("aid", aid);
        token.put("token", UUID.randomUUID().toString());
        session.setAttribute(DOWNLOAD_TOKEN, token);
        return token.get("token");
    }

}
