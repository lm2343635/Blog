var bid=request("bid");

$(document).ready(function() {
	
	BlogManager.getBlog(bid, function(blog) {
		if(blog==null) {
			location.href="urlError.html";
			return;
		}
		$("#blog-date").text(blog.date.format(DATE_HOUR_MINUTE_FORMAT));
		$("#blog-title").text(blog.title);
		$("#blog-content").html(blog.content);
		$("#loading-blog").hide();
	});

	CommentManager.getCommentsByBid(bid, function(comments) {
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
				location.href="blog.html?bid="+bid;
			}, 1000);
		});
	});

});