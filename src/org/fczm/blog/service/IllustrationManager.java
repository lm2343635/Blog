package org.fczm.blog.service;

import java.util.List;

import org.fczm.blog.bean.IllustrationBean;

public interface IllustrationManager {
	
	/**
	 * 获取博文的插图
	 * @param bid
	 * @return
	 */
	List<IllustrationBean> getIllustrationsByBid(String bid);
	
	/**
	 * 移除指定插图
	 * @param iid
	 */
	void removeIllustration(String iid);
}
