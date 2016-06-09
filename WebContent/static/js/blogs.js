var pageSize=10;
var page=request("page");
var tid=request("tid");

$(document).ready(function() {

	i18n("blogs", "static/i18n/", [
		  "blogs_home", 
		  "blogs_search_placehoder", 
		  "blogs_my_blogs", 
		  "blogs_all_types", 
		  "blogs_created_in", 
		  "blogs_readers"
	], "blogs_title");

	//加载博客
	if(page==null||page=="") {
		page=1;
	}
	searchBlogs("", page);

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
			$("<li>").append($("<a>").text(types[i].tname))
				.attr("data-id", types[i].tid).addClass(types[i].tid).appendTo("#type-list ul");

			$("#type-right-list").mengular(".type-right-list-template", {
				tid: types[i].tid,
				tname: types[i].tname
			});
		}

		$("#type-right-list").mengularClearTemplate();

		//绑定点击事件
		for(var i in types) {
			$("."+types[i].tid).click(function() {
				tid=$(this).attr("data-id");
				//设置下拉菜单
				$("#type-list button span").text($(this).text());
				//变更左侧栏目
				$("#type-right-list button").removeClass("active");
				$("#"+tid).addClass("active");
				//查询
				searchBlogs($("#search-blog").val(), 1);
				history.pushState(null, null, location.origin+location.pathname+"?tid="+tid);
			});
		}

		$("#"+ (tid==""||tid=="null"? "show-right-all-type": tid)).addClass("active");
		$("#type-list button span").text($("#"+(tid=="null"||tid==""? "show-all-type": tid)).text());
	});

	//显示所有分类
	$("#show-all-type, #show-right-all-type").click(function() {
		tid=null;
		//设置下拉菜单
		$("#type-list button span").text($("#show-all-type a").text());
		//变更右侧列表
		$("#type-right-list button").removeClass("active");
		$("#show-right-all-type").addClass("active");
		//查询
		searchBlogs($("#search-blog").val(), 1);
		history.pushState(null, null, location.origin+location.pathname);
	});
});

//当浏览器的历史发生变化时，popstate处理博客类型
window.addEventListener("popstate", function() {
	tid=request("tid");
	page=request("page");
	if(page==null||page=="") {
		page=1;
	}
	$("#type-list button span").text($("#"+(tid=="null"||tid==""? "show-all-type": tid)).text());
	searchBlogs($("#search-blog").val(), page);
});

/**
 * 搜索博客
 * @param title 博客标题
 * @param page 页码
 */
function searchBlogs(title, page) {
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
				history.pushState(null, null, location.origin+location.pathname+"?tid="+tid+"&page="+(index+1));
				searchBlogs(title, index+1);
				//返回页面顶部
				$("body").animate({
					scrollTop: "0px"
				}, 300);
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
				readers: blogs[i].readers,
				src: blogs[i].cover==null? "": "upload/"+blogs[i].bid+"/"+blogs[i].cover
			});
			
			//有封面图片才显示
			if(blogs[i].cover!=null) {
				$("#"+blogs[i].bid+" .blog-cover").show();
			}

			$("#"+blogs[i].bid).click(function() {
				location.href="blogs/"+$(this).attr("id");
			});
		}
	});
}
