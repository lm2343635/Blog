package org.fczm.blog.component.config;

import net.sf.json.JSONObject;

public class Page {

    public int adminPageSize;
    public int blogPageSize;

    public Page(JSONObject object) {
        this.adminPageSize = object.getInt("adminPageSize");
        this.blogPageSize = object.getInt("blogPageSize");
    }

}
