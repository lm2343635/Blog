package org.fczm.blog.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.directwebremoting.WebContextFactory;
import org.fczm.blog.bean.BlogBean;
import org.fczm.blog.domain.Blog;
import org.fczm.blog.domain.Comment;
import org.fczm.blog.service.BlogManager;
import org.fczm.blog.service.util.ManagerTemplate;
import org.fczm.common.util.DateTool;
import org.fczm.common.util.MengularDocument;

public class BlogManagerImpl extends ManagerTemplate implements BlogManager {
	private String blogOutputFolder;
	private int blogOutputFolderDepth;
	
	public void setBlogOutputFolder(String blogOutputFolder) {
		this.blogOutputFolder = blogOutputFolder;
	}

	public void setBlogOutputFolderDepth(int blogOutputFolderDepth) {
		this.blogOutputFolderDepth = blogOutputFolderDepth;
	}

	@Override
	public String addBlog(String title, String content, String date) {
		Blog blog=new Blog();
		blog.setTitle(title);
		blog.setContent(content);
		blog.setDate(DateTool.transferDate(date, DateTool.DATE_HOUR_MINUTE_FORMAT));
		blog.setReaders(0);
		String bid= blogDao.save(blog);
		if(bid!=null) {
			//根据模板生成文件
			MengularDocument document=new MengularDocument(WebContextFactory.get().getServletContext().getRealPath("/"), blogOutputFolderDepth,"blog.html");
			document.setValue("blog-date", DateTool.formatDate(blog.getDate(), DateTool.DATE_HOUR_MINUTE_FORMAT));
			document.setValue("blog-title", blog.getTitle());
			document.setValue("blog-content", blog.getContent());
			document.output(blogOutputFolder+blog.getBid()+".html");
		}
		return bid;
	}

	@Override
	public List<BlogBean> getAll() {
		List<BlogBean> blogs=new ArrayList<>();
		for(Blog blog: blogDao.findAll()) {
			blogs.add(new BlogBean(blog));
		}
		return blogs;
	}

	@Override
	public BlogBean getBlogInfo(String bid, boolean reader) {
		Blog blog=blogDao.get(bid);
		if(blog==null)
			return null;
		if(reader) {
			blog.setReaders(blog.getReaders()+1);
			blogDao.update(blog);
		}
		return new BlogBean(blog);
	}
	

	@Override
	public String getBlogContent(String bid) {
		Blog blog=blogDao.get(bid);
		if(blog==null)
			return null;
		return blog.getContent();
	}


	@Override
	public void modifyBlog(String bid, String title, String content, String date) {
		Blog blog=blogDao.get(bid);
		blog.setTitle(title);
		blog.setContent(content);
		blog.setDate(DateTool.transferDate(date, DateTool.DATE_HOUR_MINUTE_FORMAT));
		blogDao.update(blog);
		//根据模板生成文件
		MengularDocument document=new MengularDocument(WebContextFactory.get().getServletContext().getRealPath("/"), blogOutputFolderDepth,"blog.html");
		document.setValue("blog-date", DateTool.formatDate(blog.getDate(), DateTool.DATE_HOUR_MINUTE_FORMAT));
		document.setValue("blog-title", blog.getTitle());
		document.setValue("blog-content", blog.getContent());
		document.output(blogOutputFolder+blog.getBid()+".html");
	}

	@Override
	public void removeBlog(String bid) {
		Blog blog=blogDao.get(bid);
		for(Comment comment: commentDao.findByBlog(blog))
			commentDao.delete(comment);
		blogDao.delete(blog);
	}

	@Override
	public int getBlogsCount(String title) {
		return blogDao.getBlogsCount(title);
	}

	@Override
	public List<BlogBean> searchBlogs(String title, int page, int pageSize) {
		int offset=(page-1)*pageSize;
		List<BlogBean> blogs=new ArrayList<>();
		for(Blog blog: blogDao.findByTitle(title, offset, pageSize)) {
			blogs.add(new BlogBean(blog));
		}
		return blogs;
	}

	@Override
	public void regenerate() {
		for(Blog blog: blogDao.findAll()) {
			MengularDocument document=new MengularDocument(WebContextFactory.get().getServletContext().getRealPath("/"), blogOutputFolderDepth,"blog.html");
			document.setValue("blog-date", DateTool.formatDate(blog.getDate(), DateTool.DATE_HOUR_MINUTE_FORMAT));
			document.setValue("blog-title", blog.getTitle());
			document.setValue("blog-content", blog.getContent());
			document.output(blogOutputFolder+blog.getBid()+".html");
		}
	}

}