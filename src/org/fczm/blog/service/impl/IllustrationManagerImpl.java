package org.fczm.blog.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.fczm.blog.bean.IllustrationBean;
import org.fczm.blog.domain.Blog;
import org.fczm.blog.domain.Illustration;
import org.fczm.blog.service.IllustrationManager;
import org.fczm.blog.service.util.ManagerTemplate;

public class IllustrationManagerImpl extends ManagerTemplate implements IllustrationManager {

	@Override
	public List<IllustrationBean> getIllustrationsByBid(String bid) {
		Blog blog=blogDao.get(bid);
		if(blog==null) {
			return null;
		}
		List<IllustrationBean> illustrations=new ArrayList<>();
		for(Illustration illustration: illustrationDao.findByBlog(blog)) {
			illustrations.add(new IllustrationBean(illustration));
		}
		return illustrations;
	}

	@Override
	public void removeIllustration(String iid) {
		// TODO Auto-generated method stub
		
	}

}
