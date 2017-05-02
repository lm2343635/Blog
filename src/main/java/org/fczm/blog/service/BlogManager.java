package org.fczm.blog.service;

import java.util.List;

import org.fczm.blog.bean.BlogBean;

public interface BlogManager {

    /**
     * 新增博客文章
     *
     * @param title
     * @param date
     * @param tid
     * @return
     */
    String addBlog(String title, String date, String tid);

    /**
     * 获取所有博客
     *
     * @return
     */
    List<BlogBean> getAll();

    /**
     * 根据id获取一个博客处正文以外的基本信息
     *
     * @param bid
     * @param reader 是否为读者
     * @return
     */
    BlogBean getBlogInfo(String bid, boolean reader);

    /**
     * 获取博客正文
     *
     * @param bid
     * @return
     */
    String getBlogContent(String bid);

    /**
     * 修改博客
     *
     * @param bid
     * @param title
     * @param content
     * @param date
     * @param tid
     */
    void modifyBlog(String bid, String title, String content, String date, String tid);

    /**
     * 后台自动保存博文内容
     *
     * @param bid
     * @param content
     */
    void backgroudSaving(String bid, String content);

    /**
     * 移除博客
     *
     * @param bid
     */
    void removeBlog(String bid);

    /**
     * 根据标题获取博客数量
     *
     * @param title
     * @param type   博文分类
     * @return
     */
    int getBlogsCount(String title, String type);

    /**
     * 获取博客列表页单页长度
     *
     * @return
     */
    int getBlogsPageSize();

    /**
     * 查询博客
     *
     * @param title    标题
     * @param tid      博文分类id
     * @param page     页码
     * @param pageSize 页面大小
     * @return
     */
    List<BlogBean> searchBlogs(String title, String tid, int page, int pageSize);

    /**
     * 重新生成所有博客
     */
    void regenerate();

    /**
     * 重新生成指定博客
     *
     * @param bid
     */
    void regenerateBlog(String bid);

    /**
     * 删除指定博文的封面
     *
     * @param bid
     * @return
     */
    boolean deleteCover(String bid);

    /**
     * 设置封面可用性
     *
     * @param bid
     * @param bgenable
     */
    void setBgenable(String bid, boolean bgenable);

    /**
     * Handle uploaded cover.
     *
     * @param bid
     * @param fileName
     * @return
     */
    String handleUploadedCover(String bid, String fileName);

}
