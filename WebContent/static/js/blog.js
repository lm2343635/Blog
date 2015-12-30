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
	});

});