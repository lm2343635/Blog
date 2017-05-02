package org.fczm.blog.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.fczm.blog.bean.AttachmentBean;

public interface AttachmentManager {

    public static final String DOWNLOAD_TOKEN = "8a2e746754e7a8e20154ff98d8b30004";

    /**
     * 获取附件信息
     *
     * @param aid
     * @return
     */
    AttachmentBean getAttachment(String aid);

    /**
     * 根据博客获取附件
     *
     * @param bid
     * @return
     */
    List<AttachmentBean> getAttachmentsByBid(String bid);

    /**
     * 删除附件
     *
     * @param aid
     * @return
     */
    boolean removeAttachment(String aid);

    /**
     * 验证下载验证码，颁发token
     *
     * @param aid
     * @param code
     * @param session
     * @return token
     */
    String validateDownload(String aid, String code, HttpSession session);

    /**
     * Handle uploaded attachement.
     *
     * @param bid
     * @param fileName
     * @return
     */
    AttachmentBean handleUploadedAttachement(String bid, String fileName);

}
