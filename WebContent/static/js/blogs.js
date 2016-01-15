var pageSize=10;

$(document).ready(function() {
	//加载博客
	searchBlogs("", 1);

	$("#search-blog").bind("input propertychange", function() {
		var title=$(this).val();
		searchBlogs(title, 1);
		if(title!=""&&$("#search-cancel").is(":hidden")) {
			$("#search-cancel").show("normal");
		}
		if(title==""&&!$("#search-cancel").is(":hidden")) {
			$("#search-cancel").hide("normal");
		}
	});

	$("#search-cancel").click(function() {
		$("#search-blog").val("");
		searchBlogs("", 1);
		$(this).hide("normal");
	});
});

function searchBlogs(title, page) {
	//返回页面顶部
	$("body").animate({
		scrollTop: "250px"
	}, 300);
	
	//加载页码
	BlogManager.getBlogsCount(title, function(count) {
		$("#page-count").text(count);
		$("#page-nav ul").empty();
		for(var i=1; i<Math.ceil(count/pageSize+1);i++) {
			var li='<li><a href="javascript:void(0)">'+i+'</a></li>';
			if(page==i)
				li='<li class="active"><a href="javascript:void(0)">'+i+'</a></li>';
			$("#page-nav ul").append(li);
		}
		$("#page-nav ul li").each(function(index) {
			$(this).click(function() {
				searchBlogs(title, index+1);
			});
		});
	});

	//加载博客标题
	BlogManager.searchBlogs(title, page, pageSize, function(blogs) {
		$("#blog-list").mengularClear();
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
}