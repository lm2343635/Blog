package org.fczm.blog.service;

import net.sf.json.JSONObject;

import javax.servlet.http.HttpSession;

public interface ConfigManager {

    /**
     * Refresh config in config component.
     *
     * @param session
     * @return
     */
    boolean refreshConfig(HttpSession session);

    /**
     * Get config JSON object.
     *
     * @param session
     * @return
     */
    JSONObject getConfigObject(HttpSession session);

    /**
     * Save to config by JSON string.
     *
     * @param jsonString
     * @param session
     * @return
     */
    boolean saveConfig(String jsonString, HttpSession session);

}
