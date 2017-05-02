package org.fczm.blog.service.impl;

import org.directwebremoting.annotations.RemoteProxy;
import org.fczm.blog.bean.IllustrationBean;
import org.fczm.blog.domain.Blog;
import org.fczm.blog.domain.Illustration;
import org.fczm.blog.service.IllustrationManager;
import org.fczm.blog.service.util.ManagerTemplate;
import org.fczm.common.util.FileTool;
import org.fczm.common.util.ImageTool;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RemoteProxy(name = "IllustrationManager")
public class IllustrationManagerImpl extends ManagerTemplate implements IllustrationManager {

    public List<IllustrationBean> getIllustrationsByBid(String bid) {
        Blog blog = blogDao.get(bid);
        if (blog == null) {
            return null;
        }
        List<IllustrationBean> illustrations = new ArrayList<IllustrationBean>();
        for (Illustration illustration : illustrationDao.findByBlog(blog)) {
            illustrations.add(new IllustrationBean(illustration));
        }
        return illustrations;
    }

    @Transactional
    public boolean removeIllustration(String iid) {
        Illustration illustration = illustrationDao.get(iid);
        if (illustration == null) {
            return false;
        }
        String filePath = configComponent.rootPath + File.separator + configComponent.UploadFolder + File.separator
                + illustration.getBlog().getBid() + File.separator + illustration.getFilename();
        File file = new File(filePath);
        if (file.delete() || !file.exists()) {
            illustrationDao.delete(illustration);
            return true;
        }
        return false;
    }

    @Transactional
    public String handleUploadIllustration(String bid, String fileName) {
        Blog blog = blogDao.get(bid);
        String path = configComponent.rootPath + File.separator + configComponent.UploadFolder + File.separator + bid;
        File file = null;
        // If cannot find a blog by this bid, delete the uploaded cover.
        if (blog == null) {
            file = new File(path + File.separator + fileName);
            if (file.exists()) {
                file.delete();
            }
            return null;
        }
        Illustration illustration = new Illustration();
        illustration.setFilename(UUID.randomUUID().toString() + configComponent.ImageFormat);
        illustration.setUpload(new Date());
        illustration.setBlog(blog);
        // Modify file name.
        FileTool.modifyFileName(path, fileName, illustration.getFilename());
        // Compress illustration.
        String pathname = path + File.separator + blog.getCover();
        int width = ImageTool.getImageWidth(pathname);
        int height = ImageTool.getImageHeight(pathname);
        if (width > configComponent.MaxImageWidth) {
            ImageTool.createThumbnail(pathname, configComponent.MaxImageWidth, configComponent.MaxImageWidth * height / width, 0);
        }
        // Save to persistent store if all success.
        if (illustrationDao.save(illustration) == null) {
            return null;
        }
        return illustration.getFilename();
    }

}
