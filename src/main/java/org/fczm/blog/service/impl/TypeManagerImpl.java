package org.fczm.blog.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteProxy;
import org.fczm.blog.bean.TypeBean;
import org.fczm.blog.domain.Blog;
import org.fczm.blog.domain.Type;
import org.fczm.blog.service.TypeManager;
import org.fczm.blog.service.util.ManagerTemplate;
import org.fczm.blog.servlet.UploadServlet;
import org.fczm.common.util.FileTool;
import org.springframework.stereotype.Service;

@Service
@RemoteProxy(name = "TypeManager")
public class TypeManagerImpl extends ManagerTemplate implements TypeManager {

    public String addType(String tname) {
        Type type = new Type();
        type.setTname(tname);
        type.setDate(new Date());
        type.setCount(0);
        return typeDao.save(type);
    }

    public List<TypeBean> getAll() {
        List<TypeBean> types = new ArrayList<TypeBean>();
        for (Type type : typeDao.findAll("date", false)) {
            types.add(new TypeBean(type));
        }
        return types;
    }

    public void modifyType(String tid, String tname) {
        Type type = typeDao.get(tid);
        type.setTname(tname);
        typeDao.update(type);
    }

    public void removeType(String tid) {
        String rootPath = WebContextFactory.get().getServletContext().getRealPath("/") + File.separator;
        Type type = typeDao.get(tid);
        if (type == null) {
            return;
        }
        for (Blog blog : blogDao.findByType(type)) {
            FileTool.delFolder(rootPath + UploadServlet.UPLOAD_FOLDER + File.separator + blog.getBid());
            blogDao.delete(blog);
        }
        typeDao.delete(type);
    }

}
