var MIN_EDIT_HEIGHT=380;

$(document).ready(function() {
	checkAdminSession(function() {
		//加载博文类型
        TypeManager.getAll(function(types) {
            for(var i in types) {
                $("<option>").text(types[i].tname).val(types[i].tid).appendTo("#add-blog-type");
            }
        });
	});
	
    //清除博文内容
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

    //新增博文
    $("#add-blog-submit").click(function() {
    	var title=$("#add-blog-title").val();
    	var content=$("#add-blog-content").summernote("code");
    	var date=$("#add-blog-date").val();
        var tid=$("#add-blog-type").val();
        var validate=true;
        if(title==null||title=="") {
            $("#add-blog-title").parent().addClass("has-error");
            validate=false;
        } else {
            $("#add-blog-title").parent().removeClass("has-error");
        }
        if(content==null||content=="") {
            validate=false;
        } 
        if(tid==null||tid=="") {
            $("#add-blog-type").parent().addClass("has-error");
            validate=false;
        } else {
            $("#add-blog-type").parent().removeClass("has-error");
        }
        if(validate) {
            $("#add-blog-submit").html('<i class="fa fa-refresh fa-spin"></i>')
                                 .attr("disabled", "disabled");
            BlogManager.addBlog(title, content, date, tid, function(bid) {
                if(bid) {
                    $.messager.popup("Create this blog successfully!");
                    setTimeout(function() {
                        location.href="edit.html?bid="+bid;
                    }, 1000);
                }
            });
        } else {
            $.messager.popup("No title, content or type!");
        }
    	
    });
});