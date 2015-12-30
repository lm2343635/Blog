package org.fczm.blog.service;

import javax.servlet.http.HttpSession;

public interface AdminManager 
{
	public static final String ADMIN_FLAG="4028e083504139180150414868df000d";
	
	/**
	 * 管理员登陆
	 * @param username
	 * @param password
	 * @return
	 */
	boolean login(String username,String password, HttpSession session);
	
	/**
	 * 
	 * @param session
	 * @return
	 */
	String checkSession(HttpSession session);
}
