package org.fczm.blog.service.impl;

import java.io.File;

import javax.servlet.http.HttpSession;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.fczm.blog.service.AdminManager;
import org.fczm.blog.service.ConfigManager;
import org.fczm.blog.service.common.ManagerTemplate;
import org.fczm.common.util.JsonTool;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RemoteProxy(name = "ConfigManager")
public class ConfigManagerImpl extends ManagerTemplate implements ConfigManager {

	@RemoteMethod
	public boolean refreshConfig(HttpSession session) {
		if (!checkAdminSession(session)) {
			return false;
		}
		configComponent.load();
		return true;
	}

	@RemoteMethod
	public JSONObject getConfigObject(HttpSession session) {
		if (!checkAdminSession(session)) {
			return null;
		}
		return configComponent.configTool.getJSONConfig();
	}

	@RemoteMethod
	public boolean saveConfig(String jsonString, HttpSession session) {
		if (!checkAdminSession(session)) {
			return false;
		}
		configComponent.configTool.setJSONConfig(JSONObject.fromObject(jsonString));
		configComponent.configTool.writeObject();
		return true;
	}
	
}
