package org.fczm.blog.service.impl;

import java.io.File;

import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;
import org.fczm.blog.service.AdminManager;
import org.fczm.blog.service.util.ManagerTemplate;
import org.fczm.common.util.JsonTool;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AdminManagerImpl extends ManagerTemplate implements AdminManager {
	private	JSONArray admins = null;

	@Override
	public boolean login(String username, String password, HttpSession session) {
		if(admins == null) {
			String pathname = WebContextFactory.get().getServletContext().getRealPath("/") + File.separator + "WEB-INF/config.json";
			JsonTool config = new JsonTool(pathname);
			admins = config.getJSONArray("admins");
		}
		for(int i=0; i<admins.size(); i++) {
			JSONObject admin = (JSONObject) admins.get(i);
			if(username.equals(admin.getString("username"))&&password.equals(admin.getString("password"))) {
				session.setAttribute(ADMIN_FLAG, username);
				return true;
			}	
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
