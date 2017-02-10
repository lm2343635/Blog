package org.fczm.blog.bean;

import java.util.Date;

import org.directwebremoting.annotations.DataTransferObject;
import org.fczm.blog.domain.Comment;

@DataTransferObject
public class CommentBean {

    private String cid;
    private String name;
    private String content;
    private Date date;
    private BlogBean blog;

    public String getCid() {
        return cid;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    public BlogBean getBlog() {
        return blog;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setBlog(BlogBean blog) {
        this.blog = blog;
    }

    public CommentBean(Comment comment) {
        super();
        this.cid = comment.getCid();
        this.name = comment.getName();
        this.content = comment.getContent();
        this.date = comment.getDate();
        this.blog = new BlogBean(comment.getBlog());
    }

}
