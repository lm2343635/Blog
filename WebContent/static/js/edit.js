var bid=request("bid");
var MIN_EDIT_HEIGHT=380;

$(document).ready(function() {
	checkAdminSession(function() {
		BlogManager.getBlogInfo(bid, false, function(blog) {
			if(blog==null) {
				location.href="urlError.html";
				return;
			}
			$("#edit-blog-title").val(blog.title);
			$("#edit-blog-date").datetimepicker({
		        weekStart: 1,
		        todayBtn:  1,
		        autoclose: 1,
		        todayHighlight: 1,
		        startView: 2,
		        forceParse: 0,
		        showMeridian: 1
		    }).val(blog.date.format(DATE_HOUR_MINUTE_FORMAT));
		});
	});
	
	BlogManager.getBlogContent(bid, function(content) {
		$("#edit-blog-content").summernote({
			height: getScreenHeight()-300<MIN_EDIT_HEIGHT? MIN_EDIT_HEIGHT: getScreenHeight()-300
		}).summernote("code", content);
		$("#loading-blog").hide();
	})

	$("#edit-blog-clear").click(function() {
		$.messager.confirm("Tip", "Confirm to clear title and content of this blog article?", function() {
            $("#edit-blog-title").val("");
            $("#edit-blog-content").summernote("code", "");
        });
	});

	$("#edit-blog-submit").click(function() {
		var title=$("#edit-blog-title").val();
    	var content=$("#edit-blog-content").summernote("code");
    	var date=$("#edit-blog-date").val();
    	if(title==""||content=="") {
    		$.messager.popup("Input title and content!");
    		return;
    	}
    	$("#edit-blog-submit").html('<i class="fa fa-refresh fa-spin"></i>').attr("disabled", "disabled");
    	BlogManager.modifyBlog(bid, title, content, date, function() {
    		$.messager.popup("Mofify this blog successfully!");
    		setTimeout(function() {
				location.href="list.html";
			}, 1000);
    	});
	});
});