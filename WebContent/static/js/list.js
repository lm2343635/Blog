var pageSize=15;
var tid=null;

$(document).ready(function() {
	checkAdminSession(function() {
        searchBlogs("", 1);
	});
	
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
    BlogManager.searchBlogs(title, tid, page, pageSize, function(blogs) {
        $("#blog-list").mengularClear();
        var btitle, strs;
		for(var i in blogs) {
			if(title!="") {
				strs=blogs[i].title.split(title);
				btitle=strs[0]
				for(var j=1; j<strs.length; j++) 
					btitle+="<span class='bg-info'>"+title+"</span>"+strs[j];
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

            $("#"+blogs[i].bid+" .blog-list-delete").click(function() {
                var id=$(this).parent().parent().parent().attr("id");
                var title=$("#"+id+" .blog-list-title").text();
                $.messager.confirm("Tip", "Confirm to remove this blog: "+title+"?", function() {
                    BlogManager.removeBlog(id, function() {
                        $("#"+id).remove();
                    });
                });
            });
        }
    });
}

/**
 * 重新用模板生成所有博客
 */
function regenerate() {
	$.messager.popup("Regenerating...");
	BlogManager.regenerate(function() {
		$.messager.popup("Regenerate all blogs successfully!");
	});
}