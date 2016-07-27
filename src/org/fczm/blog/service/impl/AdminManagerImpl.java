package org.fczm.blog.service.impl;

import java.io.File;

import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;
import org.fczm.blog.service.AdminManager;
import org.fczm.blog.service.util.ManagerTemplate;
import org.fczm.common.util.JsonTool;

public class AdminManagerImpl extends ManagerTemplate implements AdminManager {
	private String admin = null;
	private String password = null;

	@Override
	public boolean login(String admin, String password, HttpSession session) {
		if(this.admin == null || this.password == null) {
			String pathname = WebContextFactory.get().getServletContext().getRealPath("/") + File.separator + "WEB-INF/config.json";
			JsonTool config = new JsonTool(pathname);
			this.admin = config.getString("admin");
			this.password = config.getString("password");
		}
		if(admin.equals(this.admin) && password.equals(this.password)) {
			session.setAttribute(ADMIN_FLAG, admin);
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
