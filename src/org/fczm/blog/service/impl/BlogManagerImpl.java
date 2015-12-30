package org.fczm.blog.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.fczm.blog.bean.BlogBean;
import org.fczm.blog.domain.Blog;
import org.fczm.blog.service.BlogManager;
import org.fczm.blog.service.util.ManagerTemplate;

public class BlogManagerImpl extends ManagerTemplate implements BlogManager {

	@Override
	public String addBlog(String title, String content) {
		Blog blog=new Blog();
		blog.setTitle(title);
		blog.setContent(content);
		blog.setDate(new Date());
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

}
