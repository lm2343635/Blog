package org.fczm.blog.service.impl;

import javax.servlet.http.HttpSession;

import org.fczm.blog.service.AdminManager;
import org.fczm.blog.service.util.ManagerTemplate;

public class AdminManagerImpl extends ManagerTemplate implements AdminManager {
	private String username;
	private String password;

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean login(String username, String password, HttpSession session) {
		if(username.equals(this.username) && password.equals(this.password)) {
			session.setAttribute(ADMIN_FLAG, username);
			return true;
		}	
		return false;
	}

	@Override
	public String checkSession(HttpSession session) {
		if(session.getAttribute(ADMIN_FLAG) == null) {
			return null;
		}
		return (String)session.getAttribute(ADMIN_FLAG);
	}

}
