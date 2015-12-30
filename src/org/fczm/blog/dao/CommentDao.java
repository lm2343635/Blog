package org.fczm.blog.dao;

import org.fczm.blog.domain.Comment;

public interface CommentDao {
	Comment get(String cid);
	String save(Comment comment);
	void update(Comment comment);
	void delete(Comment comment);
}
