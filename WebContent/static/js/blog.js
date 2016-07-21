var bid=getPageName();

$(document).ready(function() {
	i18n("blog", "../static/i18n/", [
		  "blog_blogs",
		  "blog_created_in",
		  "blog_readers",
		  "blog_attachments",
		  "blog_attachment_upload_at",
		  "blog_visitor_comments",
		  "blog_no_comments",
		  "blog_write_comment",
		  "blog_your_name",
		  "blog_comment_submit",
		  "blog_back"
	]);

	$("#add-comment-content").summernote({
		lang: getLanguage(),
		height: 300,
		toolbar: SUMMERNOTE_TOOLBAR_TEXT_ONLY
	});

	//加载博文信息
	BlogManager.getBlogInfo(bid, true, function(blog) {
		if(blog == null) {
			location.href = "../urlError.html";
			return;
		}
		$("#home .top-header").css("background", blog.cover!=null&&blog.bgenable ?  "../cover/"+blog.cover: "../static/images/header-bg.jpg");
		$("#blog-info").fillText({
			blog_readers_count: blog.readers
		});
	});

	//加载评论
	CommentManager.getCommentsByBid(bid, function(comments) {
		if(comments.length>0) {
			$("#no-comment").hide();
		}
		for(var i in comments) {
			$("#comment-list").mengular(".comment-list-template", {
				cid: comments[i].cid,
				name: comments[i].name == ""? "Anonymity": comments[i].name,
				date: comments[i].date.format(DATE_HOUR_MINUTE_FORMAT),
				content: comments[i].content,
			});
		}
	});

	//提交评论
	$("#add-comment-submit").click(function() {
		var name = $("#add-comment-name").val();
		var content = $("#add-comment-content").summernote("code");
		console.log(content);
		if(content == null || content == "") {
			$.messager.popup("Wirte some comment, please!");
			return;
		}
		CommentManager.addComment(bid, name, content, function() {
			$.messager.popup("Add Comment Success!");
			setTimeout(function() {
				location.reload();
			}, 1000);
		});
	});
	
	$("#attachment-list .attachment-list-download").each(function() {
		$(this).click(function() {
			location.href = "../UploadServlet?task=download&aid="+$(this).attr("data-id");
		});
	});
});