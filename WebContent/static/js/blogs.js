var pageSize=10;
var tid=request("tid");

$(document).ready(function() {
	//加载博客
	searchBlogs("", 1);

	//实时搜索
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

	//取消搜索
	$("#search-cancel").click(function() {
		$("#search-blog").val("");
		searchBlogs("", 1);
		$(this).hide("normal");
	});
	
	//加载所有博文分类
	TypeManager.getAll(function(types) {
		for(var i in types) {
			$("<li>").append($("<a>").text(types[i].tname)).attr("id", types[i].tid).click(function() {
				tid=$(this).attr("id");
				$("#type-list button span").text($(this).text());
				searchBlogs($("#search-blog").val(), 1);
				history.pushState({
					tid: tid
				}, null, location.origin+location.pathname+"?tid="+tid);
			}).appendTo("#type-list ul");
		}
		if(tid) {
			$("#type-list button span").text($("#"+tid).text());
		}
	});

	//显示所有分类
	$("#show-all-type").click(function() {
		tid=null;
		$("#type-list button span").text($("#show-all-type a").text());
		searchBlogs($("#search-blog").val(), 1);
		history.pushState({
			tid: null
		}, null, location.origin+location.pathname);
	});
});

//当浏览器的历史发生变化时，popstate处理博客类型
window.addEventListener("popstate", function() {
	tid=history.state.tid;
	searchBlogs($("#search-blog").val(), 1);
});

/**
 * 搜索博客
 * @param title 博客标题
 * @param page 页码
 */
function searchBlogs(title, page) {
	//返回页面顶部
	$("body").animate({
		scrollTop: "0px"
	}, 300);
	
	//加载页码
	BlogManager.getBlogsCount(title, tid, function(count) {
		$("#page-count").text(count);
		$("#page-nav ul").empty();
		for(var i=1; i<Math.ceil(count/pageSize+1);i++) {
			var li=$("<li>").append($("<a>").text(i));
			if(page==i) {
				li.addClass("active");
			}
			$("#page-nav ul").append(li);
		}
		$("#page-nav ul li").each(function(index) {
			$(this).click(function() {
				searchBlogs(title, index+1);
			});
		});
	});

	//加载博客标题
	BlogManager.searchBlogs(title, tid, page, pageSize, function(blogs) {
		$("#blog-list").mengularClear();
		var btitle, strs;
		for(var i in blogs) {
			if(title!="") {
				var reg = new RegExp('('+title+')', 'gi');
				btitle = blogs[i].title.replace(reg, '<span class="bg-info">$1</span>');
			} else {
				btitle=blogs[i].title;
			}
			$("#blog-list").mengular(".blog-list-template", {
				bid: blogs[i].bid,
				date: blogs[i].date.format(DATE_HOUR_MINUTE_FORMAT),
				tname: blogs[i].type.tname,
				title: btitle,
				readers: blogs[i].readers
			});

			$("#"+blogs[i].bid).click(function() {
				location.href="blogs/"+$(this).attr("id");
			});
		}
	});
}
