package org.fczm.blog.service.impl;

import java.io.File;
import java.util.*;

import javax.servlet.http.HttpSession;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.fczm.blog.bean.AttachmentBean;
import org.fczm.blog.domain.Attachment;
import org.fczm.blog.domain.Blog;
import org.fczm.blog.service.AttachmentManager;
import org.fczm.blog.service.common.ManagerTemplate;
import org.fczm.common.util.FileTool;
import org.fczm.common.util.RandomValidateCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RemoteProxy(name = "AttachmentManager")
public class AttachmentManagerImpl extends ManagerTemplate implements AttachmentManager {

    @RemoteMethod
    public AttachmentBean getAttachment(String aid) {
        Attachment attachment = attachmentDao.get(aid);
        if (attachment == null) {
            return null;
        }
        return new AttachmentBean(attachment);
    }

    @RemoteMethod
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

    @RemoteMethod
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

    @RemoteMethod
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

    @RemoteMethod
    @Transactional
    public AttachmentBean handleUploadedAttachement(String bid, String fileName) {
        Blog blog = blogDao.get(bid);
        String path = configComponent.rootPath + File.separator + configComponent.UploadFolder + File.separator + bid;
        File file = new File(path + File.separator + fileName);
        // If cannot find a blog by this bid, delete the uploaded cover.
        if (blog == null) {
            if (file.exists()) {
                file.delete();
            }
            return null;
        }
        Attachment attachment = new Attachment();
        attachment.setFilename(fileName);
        attachment.setStore(UUID.randomUUID().toString());
        attachment.setSize(file.length());
        attachment.setUpload(new Date());
        attachment.setBlog(blog);
        // Modify file name.
        FileTool.modifyFileName(path, fileName, attachment.getStore());
        // Save to persistent store.
        if (attachmentDao.save(attachment) == null) {
            return null;
        }
        return new AttachmentBean(attachment);
    }

}
