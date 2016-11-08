package org.fczm.blog.service.util;

import java.io.File;

import javax.servlet.http.HttpSession;

import org.fczm.blog.service.AdminManager;
import org.fczm.common.util.JsonTool;

import net.sf.json.JSONObject;

public class ConfigManager {
	
	public static final String CONFIG_PATH = "WEB-INF/config.json";
	private JsonTool config = null;
	
	public JsonTool getConfig() {
		if(config == null) {
			loadConfig();
		}
		return config;
	}
	
	public void loadConfig() {
		String rootPath = this.getClass().getClassLoader().getResource("/").getPath().split("WEB-INF")[0];
		String pathname = rootPath + File.separator + CONFIG_PATH;
		config = new JsonTool(pathname);
	}
	
	public JSONObject getConfigObject(HttpSession session) {
		if (session.getAttribute(AdminManager.ADMIN_FLAG) == null) {
			return null;
		}
		return getConfig().getJSONConfig();
	}
	
	public boolean saveConfig(String jsonString, HttpSession session) {
		if (session.getAttribute(AdminManager.ADMIN_FLAG) == null) {
			return false;
		}
		config.setJSONConfig(JSONObject.fromObject(jsonString));
		config.writeObject();
		return true;
	}
	
}
