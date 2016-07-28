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
	private static final String CONFIG_PATH = "WEB-INF/config.json";
	private JsonTool config = null;
	private	JSONArray admins = null;
	
	public JsonTool getConfig() {
		if(config == null) {
			String pathname = WebContextFactory.get().getServletContext().getRealPath("/") + File.separator + CONFIG_PATH;
			config = new JsonTool(pathname);
		}
		return config;
	}
	
	public JSONArray getAdmins() {
		if(admins == null) {
			admins = getConfig().getJSONArray("admins");
		}
		return admins;
	}
	
	@Override
	public boolean login(String username, String password, HttpSession session) {
		getAdmins();
		for(int i = 0; i < admins.size(); i++) {
			JSONObject admin = admins.getJSONObject(i);
			if(username.equals(admin.getString("username")) && password.equals(admin.getString("password"))) {
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

	@Override
	public boolean addAdmin(String username, String password) {
		if(username.equals("") || password.equals("")) {
			return false;
		}
		getAdmins();
		for(int i = 0; i < admins.size(); i++) {
			if(username.equals(admins.getJSONObject(i).getString("username"))) {
				return false;
			}	
		}
		JSONObject admin = new JSONObject();
		admin.put("username", username);
		admin.put("password", password);
		admins.add(admin);
		config.put("admins", admins);
		config.writeObject();
		return true;
	}

	@Override
	public boolean modifyPassword(String username, String oldPassword, String newPassword) {
		getAdmins();
		for(int i = 0; i < admins.size(); i++) {
			JSONObject admin = admins.getJSONObject(i);
			if(username.equals(admin.getString("username")) && oldPassword.equals(admin.getString("password"))) {
				admin.put("password", newPassword);
				admins.set(i, admin);
				config.put("admins", admins);
				config.writeObject();
				return true;
			}	
		}
		return false;
	}

	@Override
	public boolean removeAdmin(String username, String password) {
		getAdmins();
		for(int i = 0; i < admins.size(); i++) {
			JSONObject admin = admins.getJSONObject(i);
			if(username.equals(admin.getString("username")) && password.equals(admin.getString("password"))) {
				admins.remove(i);
				config.put("admins", admins);
				config.writeObject();
				return true;
			}
		}
		return false;
	}

	public int getAdminPageSize() {
		return config.getInt("adminPageSize");
	}

	public int getUserPageSize() {
		return config.getInt("userPageSize");
	}

}
