package org.fczm.blog.service.impl;

import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteProxy;
import org.fczm.blog.bean.BlogBean;
import org.fczm.blog.domain.Attachment;
import org.fczm.blog.domain.Blog;
import org.fczm.blog.domain.Type;
import org.fczm.blog.service.BlogManager;
import org.fczm.blog.service.util.ManagerTemplate;
import org.fczm.common.util.DateTool;
import org.fczm.common.util.FileTool;
import org.fczm.common.util.ImageTool;
import org.fczm.common.util.MengularDocument;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.*;

@Service
@RemoteProxy(name = "BlogManager")
public class BlogManagerImpl extends ManagerTemplate implements BlogManager {

    private static final String blogOutputFolder = "blogs/";
    private static final int blogOutputFolderDepth = 1;

    @Transactional
    public String addBlog(String title, String date, String tid) {
        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setContent("");
        blog.setDate(DateTool.transferDate(date, DateTool.DATE_HOUR_MINUTE_FORMAT));
        blog.setReaders(0);
        blog.setBgenable(true);
        Type type = typeDao.get(tid);
        blog.setType(type);
        String bid = blogDao.save(blog);
        if (bid != null) {
            // Generate static blog html file by template.
            generateBlog(blog);
            // Number of blog in this type plus 1
            type.setCount(type.getCount() + 1);
            typeDao.update(type);
        }
        return bid;
    }

    public List<BlogBean> getAll() {
        List<BlogBean> blogs = new ArrayList<BlogBean>();
        for (Blog blog : blogDao.findAll()) {
            blogs.add(new BlogBean(blog));
        }
        return blogs;
    }

    @Transactional
    public BlogBean getBlogInfo(String bid, boolean reader) {
        Blog blog = blogDao.get(bid);
        if (blog == null) {
            return null;
        }
        if (reader) {
            blog.setReaders(blog.getReaders() + 1);
            blogDao.update(blog);
        }
        return new BlogBean(blog);
    }


    public String getBlogContent(String bid) {
        Blog blog = blogDao.get(bid);
        if (blog == null)
            return null;
        return blog.getContent();
    }

    @Transactional
    public void modifyBlog(String bid, String title, String content, String date, String tid) {
        Blog blog = blogDao.get(bid);
        blog.setTitle(title);
        blog.setContent(content);
        blog.setDate(DateTool.transferDate(date, DateTool.DATE_HOUR_MINUTE_FORMAT));
        Type oldType = blog.getType();
        Type newType = typeDao.get(tid);
        blog.setType(newType);
        blogDao.update(blog);
        // Update number of blogs in old type and new type.
        oldType.setCount(oldType.getCount() - 1);
        typeDao.update(oldType);
        newType.setCount(newType.getCount() + 1);
        typeDao.update(newType);
        // Generate static blog html file by template.
        generateBlog(blog);
    }

    @Transactional
    public void backgroudSaving(String bid, String content) {
        Blog blog = blogDao.get(bid);
        blog.setContent(content);
        blogDao.update(blog);
    }

    @Transactional
    public void removeBlog(String bid) {
        Blog blog = blogDao.get(bid);
        //博文分类数量减1
        Type type = blog.getType();
        type.setCount(type.getCount() - 1);
        typeDao.update(type);
        String rootPath = WebContextFactory.get().getServletContext().getRealPath("/") + File.separator;
        FileTool.delFolder(rootPath + configComponent.UploadFolder + File.separator + blog.getBid());
        blogDao.delete(blog);
    }

    public int getBlogsCount(String title, String tid) {
        Type type = (tid == null) ? null : typeDao.get(tid);
        return blogDao.getBlogsCount(title, type);
    }

    public int getBlogsPageSize() {
        return getPageSizeConfig().getInt("blogPageSize");
    }

    public List<BlogBean> searchBlogs(String title, String tid, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        List<BlogBean> blogs = new ArrayList<BlogBean>();
        Type type = (tid == null) ? null : typeDao.get(tid);
        for (Blog blog : blogDao.findByTitle(title, type, offset, pageSize)) {
            blogs.add(new BlogBean(blog));
        }
        return blogs;
    }

    public void regenerate() {
        String rootPath = WebContextFactory.get().getServletContext().getRealPath("/") + File.separator;
        FileTool.delAllFile(rootPath + blogOutputFolder);
        for (Blog blog : blogDao.findAll()) {
            generateBlog(blog);
        }
    }

    public void regenerateBlog(String bid) {
        Blog blog = blogDao.get(bid);
        generateBlog(blog);
    }

    private void generateBlog(Blog blog) {
        if (blog == null) {
            return;
        }
        String rootPath = WebContextFactory.get().getServletContext().getRealPath("/") + File.separator;
        MengularDocument document = new MengularDocument(rootPath, blogOutputFolderDepth, "blog.html", "blogs/" + blog.getBid());
        List<Map<String, String>> items = new ArrayList<Map<String, String>>();
        for (Attachment attachment : attachmentDao.findByBlog(blog)) {
            Map<String, String> item = new HashMap<String, String>();
            item.put("aid", attachment.getAid());
            item.put("filename", attachment.getFilename());
            item.put("upload", DateTool.formatDate(attachment.getUpload(), DateTool.DATE_HOUR_MINUTE_SECOND_FORMAT));
            item.put("size", String.valueOf(attachment.getSizeString()));
            items.add(item);
        }
        document.setLoop("attachment-list", items);
        document.setValue("blog-date", DateTool.formatDate(blog.getDate(), DateTool.DATE_HOUR_MINUTE_FORMAT));
        document.setValue("blog-title", blog.getTitle());
        document.setValue("blog-tid", blog.getType().getTid());
        document.setValue("blog-tname", blog.getType().getTname());
        document.setValue("blog-content", blog.getContent());
        document.output();
    }

    @Transactional
    public boolean deleteCover(String bid) {
        Blog blog = blogDao.get(bid);
        String rootPath = WebContextFactory.get().getServletContext().getRealPath("/") + File.separator;
        String coverPath = rootPath + File.separator + configComponent.UploadFolder
                + File.separator + blog.getBid() + File.separator + blog.getCover();
        if (new File(coverPath).delete()) {
            blog.setCover(null);
            blogDao.update(blog);
            return true;
        }
        return false;
    }

    @Transactional
    public void setBgenable(String bid, boolean bgenable) {
        Blog blog = blogDao.get(bid);
        if (blog == null) {
            return;
        }
        blog.setBgenable(bgenable);
        blogDao.update(blog);
    }

    @Transactional
    public String handleUploadedCover(String bid, String fileName) {
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
        // If old cover is existed, delete the old cover at first.
        if (blog.getCover() != null) {
            file = new File(path + File.separator + blog.getCover());
            if (file.exists()) {
                file.delete();
            }
        }
        // Generate new cover name by UUID.
        blog.setCover(UUID.randomUUID().toString() + configComponent.ImageFormat);
        blogDao.update(blog);
        // Modify file name.
        FileTool.modifyFileName(path, fileName, blog.getCover());
        // Compress cover.
        String pathname = path + File.separator + blog.getCover();
        int width = ImageTool.getImageWidth(pathname);
        int height = ImageTool.getImageHeight(pathname);
        if (width > configComponent.MaxImageWidth) {
            ImageTool.createThumbnail(pathname, configComponent.MaxImageWidth, configComponent.MaxImageWidth * height / width, 0);
        }
        // Save to persistent store if all success.

        return blog.getCover();
    }

}