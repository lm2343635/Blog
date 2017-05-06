package org.fczm.blog.service.impl;

import java.io.File;

import javax.servlet.http.HttpSession;

import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.fczm.blog.service.AdminManager;
import org.fczm.blog.service.common.ManagerTemplate;
import org.fczm.common.util.JsonTool;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
@RemoteProxy(name = "AdminManager")
public class AdminManagerImpl extends ManagerTemplate implements AdminManager {

    private JsonTool config = null;
    private JSONArray admins = null;

    public JsonTool getConfig() {
        if (config == null) {
            String pathname = WebContextFactory.get().getServletContext().getRealPath("/") + File.separator + AdminConfigPath;
            config = new JsonTool(pathname);
        }
        return config;
    }

    @RemoteMethod
    public JSONArray getAdmins() {
        if (admins == null) {
            admins = getConfig().getJSONArray("admins");
        }
        return admins;
    }

    @RemoteMethod
    public JSONArray getAdmins(HttpSession session) {
        if (checkSession(session) == null) {
            return null;
        }
        return getAdmins();
    }

    @RemoteMethod
    public boolean login(String username, String password, HttpSession session) {
        getAdmins();
        for (int i = 0; i < admins.size(); i++) {
            JSONObject admin = admins.getJSONObject(i);
            if (username.equals(admin.getString("username")) && password.equals(admin.getString("password"))) {
                session.setAttribute(AdminFlag, username);
                return true;
            }
        }
        return false;
    }

    @RemoteMethod
    public String checkSession(HttpSession session) {
        if (session.getAttribute(AdminFlag) == null) {
            return null;
        }
        return (String) session.getAttribute(AdminFlag);
    }

    @RemoteMethod
    public boolean addAdmin(String username, String password, HttpSession session) {
        if (checkSession(session) == null) {
            return false;
        }
        if (username.equals("") || password.equals("")) {
            return false;
        }
        getAdmins();
        for (int i = 0; i < admins.size(); i++) {
            if (username.equals(admins.getJSONObject(i).getString("username"))) {
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

    @RemoteMethod
    public boolean modifyPassword(String username, String oldPassword, String newPassword, HttpSession session) {
        if (checkSession(session) == null) {
            return false;
        }
        getAdmins();
        for (int i = 0; i < admins.size(); i++) {
            JSONObject admin = admins.getJSONObject(i);
            if (username.equals(admin.getString("username")) && oldPassword.equals(admin.getString("password"))) {
                admin.put("password", newPassword);
                admins.set(i, admin);
                config.put("admins", admins);
                config.writeObject();
                return true;
            }
        }
        return false;
    }

    @RemoteMethod
    public boolean removeAdmin(String username, HttpSession session) {
        if (checkSession(session) == null) {
            return false;
        }
        getAdmins();
        for (int i = 0; i < admins.size(); i++) {
            JSONObject admin = admins.getJSONObject(i);
            if (username.equals(admin.getString("username"))) {
                admins.remove(i);
                config.put("admins", admins);
                config.writeObject();
                return true;
            }
        }
        return false;
    }

}
