package org.fczm.blog.bean;

import java.util.Date;

import org.directwebremoting.annotations.DataTransferObject;
import org.fczm.blog.domain.Attachment;

@DataTransferObject
public class AttachmentBean {

    private String aid;
    private String filename;
    private String size;
    private Date upload;
    private String bid;

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Date getUpload() {
        return upload;
    }

    public void setUpload(Date upload) {
        this.upload = upload;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public AttachmentBean(Attachment attachment) {
        super();
        this.aid = attachment.getAid();
        this.filename = attachment.getFilename();
        this.size = attachment.getSizeString();
        this.upload = attachment.getUpload();
        this.bid = attachment.getBlog().getBid();
    }

}
