$(document).ready(function() {
	BlogManager.getAll(function(blogs) {
		for(var i in blogs) {
			$("#blog-list").mengular(".blog-list-template", {
				bid: blogs[i].bid,
				date: blogs[i].date.format(DATE_HOUR_MINUTE_FORMAT),
				title: blogs[i].title
			});

			$("#"+blogs[i].bid).click(function() {
				location.href="blog.html?bid="+$(this).attr("id");
			});
		}
	});
});