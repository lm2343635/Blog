package org.fczm.blog.service;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

public interface AdminManager {

    public static final String AdminConfigPath = "WEB-INF/admin.json";
    public static final String AdminFlag = "702764128274710150414868df000d";

    /**
     * @param session
     * @return
     */
    JSONArray getAdmins(HttpSession session);

    /**
     * 管理员登陆
     *
     * @param username
     * @param password
     * @return
     */
    boolean login(String username, String password, HttpSession session);

    /**
     * @param session
     * @return
     */
    String checkSession(HttpSession session);

    /**
     * @param username
     * @param password
     */
    boolean addAdmin(String username, String password, HttpSession session);

    /**
     * @param username
     * @param oldPassword
     * @param newPassword
     */
    boolean modifyPassword(String username, String oldPassword, String newPassword, HttpSession session);

    /**
     * @param username
     * @param password
     */
    boolean removeAdmin(String username, HttpSession session);

}
