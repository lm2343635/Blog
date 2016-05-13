package org.fczm.blog.service;

import java.util.List;

import org.fczm.blog.bean.TypeBean;

public interface TypeManager {

	/**
	 * 新增博文类型
	 * @param tname
	 * @return
	 */
	String addType(String tname);
	
	/**
	 * 获取所有博文类型
	 * @return
	 */
	List<TypeBean> getAll();
	
	/**
	 * 修改博文类型名称
	 * @param tid
	 * @param tname
	 */
	void modifyType(String tid, String tname);
	
	/**
	 * 删除博文类型
	 * @param tid
	 */
	void removeType(String tid);
}
