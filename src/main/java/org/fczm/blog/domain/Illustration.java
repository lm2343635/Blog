package org.fczm.blog.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "blog_illustration")
public class Illustration implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String iid;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private Date upload;

    @ManyToOne
    @JoinColumn(name = "bid", nullable = false)
    private Blog blog;

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Date getUpload() {
        return upload;
    }

    public void setUpload(Date upload) {
        this.upload = upload;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

}
