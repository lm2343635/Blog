var bid=request("bid");

$(document).ready(function() {
	
	BlogManager.getBlog(bid, function(blog) {
		if(blog==null) {
			location.href="urlError.html";
			return;
		}
		$("#edit-blog-date").text(blog.date.format(DATE_HOUR_MINUTE_SECOND_FORMAT));
		$("#edit-blog-title").val(blog.title);
		$("#edit-blog-content").summernote({
			height: 700
		}).summernote("code", blog.content);
	});

});