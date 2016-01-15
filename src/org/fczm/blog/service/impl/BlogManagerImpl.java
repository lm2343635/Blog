package org.fczm.blog.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.fczm.blog.bean.BlogBean;
import org.fczm.blog.domain.Blog;
import org.fczm.blog.domain.Comment;
import org.fczm.blog.service.BlogManager;
import org.fczm.blog.service.util.ManagerTemplate;
import org.fczm.common.util.DateTool;

public class BlogManagerImpl extends ManagerTemplate implements BlogManager {

	@Override
	public String addBlog(String title, String content, String date) {
		Blog blog=new Blog();
		blog.setTitle(title);
		blog.setContent(content);
		blog.setDate(DateTool.transferDate(date, DateTool.DATE_HOUR_MINUTE_FORMAT));
		return blogDao.save(blog);
	}

	@Override
	public List<BlogBean> getAll() {
		List<BlogBean> blogs=new ArrayList<>();
		for(Blog blog: blogDao.findAll()) {
			BlogBean blogBean=new BlogBean();
			blogBean.setBid(blog.getBid());
			blogBean.setTitle(blog.getTitle());
			blogBean.setDate(blog.getDate());
			blogs.add(blogBean);
		}
		return blogs;
	}

	@Override
	public BlogBean getBlog(String bid) {
		Blog blog=blogDao.get(bid);
		if(blog==null)
			return null;
		return new BlogBean(blog);
	}

	@Override
	public void modifyBlog(String bid, String title, String content, String date) {
		Blog blog=blogDao.get(bid);
		blog.setTitle(title);
		blog.setContent(content);
		blog.setDate(DateTool.transferDate(date, DateTool.DATE_HOUR_MINUTE_FORMAT));
		blogDao.update(blog);
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

}
