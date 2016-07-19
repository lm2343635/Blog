package org.fczm.blog.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.directwebremoting.WebContextFactory;
import org.fczm.blog.bean.IllustrationBean;
import org.fczm.blog.domain.Blog;
import org.fczm.blog.domain.Illustration;
import org.fczm.blog.service.IllustrationManager;
import org.fczm.blog.service.util.ManagerTemplate;
import org.fczm.blog.servlet.UploadServlet;

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
	public boolean removeIllustration(String iid) {
		Illustration illustration=illustrationDao.get(iid);
		if(illustration==null) {
			return false;
		}
		String rootPath=WebContextFactory.get().getServletContext().getRealPath("/");
		String filePath=rootPath+File.separator+UploadServlet.UPLOAD_FOLDER+File.separator
				+illustration.getBlog().getBid()+File.separator+illustration.getFilename();
		File file = new File(filePath);
		if(file.delete()||!file.exists()) {
			illustrationDao.delete(illustration);
			return true;
		}
		return false;
	}

}
