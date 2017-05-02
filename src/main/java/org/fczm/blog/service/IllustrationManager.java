package org.fczm.blog.service;

import org.fczm.blog.bean.IllustrationBean;

import java.util.List;

public interface IllustrationManager {

    /**
     * 获取博文的插图
     *
     * @param bid
     * @return
     */
    List<IllustrationBean> getIllustrationsByBid(String bid);

    /**
     * 移除指定插图
     *
     * @param iid
     */
    boolean removeIllustration(String iid);

    /**
     * Handle uploaded illustration.
     *
     * @param bid
     * @param fileName
     * @return
     */
    String handleUploadIllustration(String bid, String fileName);

}
