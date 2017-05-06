package org.fczm.blog.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.fczm.blog.bean.CommentBean;
import org.fczm.blog.domain.Blog;
import org.fczm.blog.domain.Comment;
import org.fczm.blog.service.CommentManager;
import org.fczm.blog.service.common.ManagerTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RemoteProxy(name = "CommentManager")
public class CommentManagerImpl extends ManagerTemplate implements CommentManager {

    @RemoteMethod
    @Transactional
    public String addComment(String bid, String name, String content) {
        Comment comment = new Comment();
        comment.setBlog(blogDao.get(bid));
        comment.setName(name);
        comment.setContent(content);
        comment.setDate(new Date());
        return commentDao.save(comment);
    }

    @RemoteMethod
    public List<CommentBean> getCommentsByBid(String bid) {
        List<CommentBean> comments = new ArrayList<CommentBean>();
        Blog blog = blogDao.get(bid);
        for (Comment comment : commentDao.findByBlog(blog)) {
            comments.add(new CommentBean(comment));
        }
        return comments;
    }

    @RemoteMethod
    @Transactional
    public void removeComment(String cid) {
        Comment comment = commentDao.get(cid);
        commentDao.delete(comment);
    }

}
