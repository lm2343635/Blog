var bid=getPageName();

$(document).ready(function() {
	i18n("blog", "../static/i18n/", [
		  "blog_blogs", 
		  "blog_created_in", 
		  "blog_readers", 
		  "blog_visitor_comments", 
		  "blog_no_comments", 
		  "blog_write_comment",
		  "blog_write_comment_placeholder",
		  "blog_your_name",
		  "blog_comment_clear",
		  "blog_comment_submit",
		  "blog_back"
	]);
	
	BlogManager.getBlogInfo(bid, true, function(blog) {
		if(blog==null) {
			location.href="urlError.html";
			return;
		}
		
		fillText({
			"blog-readers": blog.readers
		});

	});

	CommentManager.getCommentsByBid(bid, function(comments) {
		if(comments.length>0) {
			$("#no-comment").hide();
		}
		for(var i in comments) {
			$("#comment-list").mengular(".comment-list-template", {
				cid: comments[i].cid,
				name: comments[i].name==""? "Anonymity": comments[i].name,
				date: comments[i].date.format(DATE_HOUR_MINUTE_FORMAT),
				content: comments[i].content,
			});
		}
	});

	$("#add-comment-clear").click(function() {
		$("#add-comment-name").val("");
		$("#add-comment-content").val("");
	});

	$("#add-comment-submit").click(function() {
		var name=$("#add-comment-name").val();
		var content=$("#add-comment-content").val();
		if(content=="") {
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

});