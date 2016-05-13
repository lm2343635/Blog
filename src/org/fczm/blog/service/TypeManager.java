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
}
