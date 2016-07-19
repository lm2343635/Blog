package org.fczm.blog.service;

import java.util.List;

import org.fczm.blog.bean.AttachmentBean;

public interface AttachmentManager {
	
	/**
	 * 根据博客获取附件
	 * @param bid
	 * @return
	 */
	List<AttachmentBean> getAttachmentsByBid(String bid);
	
	/**
	 * 删除附件
	 * @param aid
	 * @return
	 */
	boolean removeAttachment(String aid);
}
