package org.fczm.blog.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.directwebremoting.WebContextFactory;
import org.fczm.blog.bean.BlogBean;
import org.fczm.blog.domain.Attachment;
import org.fczm.blog.domain.Blog;
import org.fczm.blog.domain.Type;
import org.fczm.blog.service.BlogManager;
import org.fczm.blog.service.util.ManagerTemplate;
import org.fczm.blog.servlet.UploadServlet;
import org.fczm.common.util.DateTool;
import org.fczm.common.util.FileTool;
import org.fczm.common.util.MengularDocument;

public class BlogManagerImpl extends ManagerTemplate implements BlogManager {
	private String blogOutputFolder;
	private int blogOutputFolderDepth;
	
	public void setBlogOutputFolder(String blogOutputFolder) {
		this.blogOutputFolder = blogOutputFolder;
	}

	public void setBlogOutputFolderDepth(int blogOutputFolderDepth) {
		this.blogOutputFolderDepth = blogOutputFolderDepth;
	}

	@Override
	public String addBlog(String title, String date, String tid) {
		Blog blog=new Blog();
		blog.setTitle(title);
		blog.setContent("");
		blog.setDate(DateTool.transferDate(date, DateTool.DATE_HOUR_MINUTE_FORMAT));
		blog.setReaders(0);
		blog.setBgenable(true);
		Type type=typeDao.get(tid);
		blog.setType(type);
		String bid= blogDao.save(blog);
		if(bid!=null) {
			//根据模板生成文件
			generateBlog(blog);
			//type文章数量加1
			type.setCount(type.getCount()+1);
			typeDao.update(type);
		}
		return bid;
	}

	@Override
	public List<BlogBean> getAll() {
		List<BlogBean> blogs=new ArrayList<>();
		for(Blog blog: blogDao.findAll()) {
			blogs.add(new BlogBean(blog));
		}
		return blogs;
	}

	@Override
	public BlogBean getBlogInfo(String bid, boolean reader) {
		Blog blog=blogDao.get(bid);
		if(blog==null) {
			return null;
		}
		if(reader) {
			blog.setReaders(blog.getReaders()+1);
			blogDao.update(blog);
		}
		return new BlogBean(blog);
	}
	

	@Override
	public String getBlogContent(String bid) {
		Blog blog=blogDao.get(bid);
		if(blog==null)
			return null;
		return blog.getContent();
	}


	@Override
	public void modifyBlog(String bid, String title, String content, String date, String tid) {
		Blog blog=blogDao.get(bid);
		blog.setTitle(title);
		blog.setContent(content);
		blog.setDate(DateTool.transferDate(date, DateTool.DATE_HOUR_MINUTE_FORMAT));
		Type oldType=blog.getType();
		Type newType=typeDao.get(tid);
		blog.setType(newType);
		blogDao.update(blog);
		//更新新旧分类的博文数量
		oldType.setCount(oldType.getCount()-1);
		typeDao.update(oldType);
		newType.setCount(newType.getCount()+1);
		typeDao.update(newType);
		//根据模板生成文件
		generateBlog(blog);
	}

	@Override
	public void backgroudSaving(String bid, String content) {
		Blog blog=blogDao.get(bid);
		blog.setContent(content);
		blogDao.update(blog);
	}

	@Override
	public void removeBlog(String bid) {
		Blog blog=blogDao.get(bid);
		//博文分类数量减1
		Type type=blog.getType();
		type.setCount(type.getCount()-1);
		typeDao.update(type);
		String rootPath=WebContextFactory.get().getServletContext().getRealPath("/")+File.separator;
		FileTool.delFolder(rootPath+UploadServlet.UPLOAD_FOLDER+File.separator+blog.getBid());
		blogDao.delete(blog);
	}

	@Override
	public int getBlogsCount(String title, String tid) {
		Type type= (tid==null)? null: typeDao.get(tid);
		return blogDao.getBlogsCount(title, type);
	}

	@Override
	public List<BlogBean> searchBlogs(String title, String tid, int page, int pageSize) {
		int offset=(page-1)*pageSize;
		List<BlogBean> blogs=new ArrayList<>();
		Type type= (tid==null)? null: typeDao.get(tid);
		for(Blog blog: blogDao.findByTitle(title, type, offset, pageSize)) {
			blogs.add(new BlogBean(blog));
		}
		return blogs;
	}

	@Override
	public void regenerate() {
		String rootPath=WebContextFactory.get().getServletContext().getRealPath("/")+File.separator;
		FileTool.delAllFile(rootPath+blogOutputFolder);
		for(Blog blog: blogDao.findAll()) {
			generateBlog(blog);
		}
	}
	
	@Override
	public void regenerateBlog(String bid) {
		Blog blog=blogDao.get(bid);
		generateBlog(blog);
	}
	
	private void generateBlog(Blog blog) {
		if(blog==null) {
			return;
		}
		String rootPath=WebContextFactory.get().getServletContext().getRealPath("/")+File.separator;
		MengularDocument document=new MengularDocument(rootPath, blogOutputFolderDepth,"blog.html", "blogs/"+blog.getBid());
		List<Map<String, String>> items = new ArrayList<>();
		for(Attachment attachment: attachmentDao.findByBlog(blog)) {
			Map<String, String> item = new HashMap<>();
			item.put("aid", attachment.getAid());
			item.put("filename", attachment.getFilename());
			item.put("upload", DateTool.formatDate(attachment.getUpload(), DateTool.DATE_HOUR_MINUTE_SECOND_FORMAT));
			item.put("size", String.valueOf(attachment.getSize()));
			items.add(item);
		}
		document.setLoop("attachment-list", items);
		document.setValue("blog-date", DateTool.formatDate(blog.getDate(), DateTool.DATE_HOUR_MINUTE_FORMAT));
		document.setValue("blog-title", blog.getTitle());
		document.setValue("blog-tid", blog.getType().getTid());
		document.setValue("blog-tname", blog.getType().getTname());
		document.setValue("blog-content", blog.getContent());
		document.output();
	}

	@Override
	public boolean deleteCover(String bid) {
		Blog blog=blogDao.get(bid);
		String rootPath=WebContextFactory.get().getServletContext().getRealPath("/")+File.separator;
		if(new File(rootPath+File.separator+UploadServlet.UPLOAD_FOLDER+File.separator+blog.getBid()+File.separator+blog.getCover()).delete()) {
			blog.setCover(null);
			blogDao.update(blog);
			return true;
		}
		return false;
	}

	@Override
	public void setBgenable(String bid, boolean bgenable) {
		Blog blog=blogDao.get(bid);
		if(blog==null) {
			return;
		}
		blog.setBgenable(bgenable);
		blogDao.update(blog);
	}

}