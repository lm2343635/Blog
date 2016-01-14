var MIN_EDIT_HEIGHT=380;

$(document).ready(function() {
	checkAdminSession(function() {
		
	});
	
	$("#add-blog-clear").click(function() {
        $.messager.confirm("Tip", "Confirm to clear title and content of this blog article?", function() {
            $("#add-blog-title").val("");
            $("#add-blog-content").summernote("code", "");
        });
	});

    $("#add-blog-content").summernote({
    	height: getScreenHeight()-300<MIN_EDIT_HEIGHT? MIN_EDIT_HEIGHT: getScreenHeight()-300,
    	placeholder: "Write your blog content here."
    });

    $("#add-blog-date").datetimepicker({
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        forceParse: 0,
        showMeridian: 1
    }).val((new Date()).format(DATE_HOUR_MINUTE_FORMAT));

    $("#add-blog-submit").click(function() {
    	var title=$("#add-blog-title").val();
    	var content=$("#add-blog-content").summernote("code");
    	var date=$("#add-blog-date").val();
    	if(title==""||content=="") {
    		$.messager.popup("Input title and content!");
    		return;
    	}
    	$("#add-blog-submit").html('<i class="fa fa-refresh fa-spin"></i>').attr("disabled", "disabled");
    	BlogManager.addBlog(title, content, date, function(bid) {
    		if(bid) {
    			$.messager.popup("Create this blog successfully!");
    			setTimeout(function() {
    				location.href="list.html";
    			}, 1000);
    		}
    	});
    });
});