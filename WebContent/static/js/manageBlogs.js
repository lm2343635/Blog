$(document).ready(function() {
	checkAdminSession(function() {
		BlogManager.getAll(function(blogs) {
			$("#blog-list tbody").mengularClear();
			for(var i in blogs) {
				$("#blog-list tbody").mengular(".blog-list-template", {
					bid: blogs[i].bid,
					date: blogs[i].date.format(DATE_HOUR_MINUTE_SECOND_FORMAT),
					title: blogs[i].title
				});

                $("#"+blogs[i].bid+" .blog-list-delete").click(function() {
                    var id=$(this).parent().attr("id");
                    var title=$("#"+id+" .blog-list-title").text();
                    $.messager.confirm("Tip", "Confirm to remove this blog: "+title+"?", function() {
                        BlogManager.removeBlog(id, function() {
                            $("#"+id).remove();
                        });
                    });
                });
			}
		});
	});
	
	$("#add-blog-clear").click(function() {
        $.messager.confirm("Tip", "Confirm to clear title and content of this blog article?", function() {
            $("#add-blog-title").val("");
            $("#add-blog-content").summernote("code", "");
        });
	});

    $("#add-blog-content").summernote({
    	height: 400,
    	placeholder: "Write your blog content here."
    });

    $("#add-blog-submit").click(function() {
    	var title=$("#add-blog-title").val();
    	var content=$("#add-blog-content").summernote("code");
    	if(title==""||content=="") {
    		$.messager.popup("Input title and content!");
    		return;
    	}
    	BlogManager.addBlog(title, content, function(bid) {
    		if(bid) {
    			$.messager.popup("Create this blog successfully!");
    			setTimeout(function() {
    				location.href="manageBlogs.html";
    			}, 1000);
    		}
    	});
    });
});