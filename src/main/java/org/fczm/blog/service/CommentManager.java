package org.fczm.blog.service;

import java.util.List;

import org.fczm.blog.bean.CommentBean;

public interface CommentManager {
	
	/**
	 * 新增评论
	 * @param bid
	 * @param name
	 * @param content
	 * @return
	 */
	String addComment(String bid, String name, String content);
	
	/**
	 * 获取博客的所有评论
	 * @param bid
	 * @return
	 */
	List<CommentBean> getCommentsByBid(String bid);
	
	/**
	 * 删除评论
	 * @param cid
	 */
	void removeComment(String cid);
}
