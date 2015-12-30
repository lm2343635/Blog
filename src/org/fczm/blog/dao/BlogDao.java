package org.fczm.blog.dao;

import java.util.List;

import org.fczm.blog.domain.Blog;

public interface BlogDao {
	Blog get(String bid);
	String save(Blog blog);
	void update(Blog blog);
	void delete(Blog blog);
	
	List<Blog> findAll();
}
