var bid=request("bid");
var MIN_EDIT_HEIGHT=380;
var AUTOMATICAL_SAVING_INTERVAL=60000;

$(document).ready(function() {
	checkAdminSession(function() {
		//加载博客信息
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
			
			$("#blog-cover-bgenable").bootstrapSwitch({
				state: blog.bgenable
			}).on('switchChange.bootstrapSwitch', function(event, state) {
				BlogManager.setBgenable(bid, state);
			});

		    //加载博客分类
			TypeManager.getAll(function(types) {
	            for(var i in types) {
	            	var option=$("<option>").text(types[i].tname).val(types[i].tid);
	            	if(types[i].tid==blog.type.tid) {
	            		option.attr("selected", "selected");
	            	}
	            	$("#edit-blog-type").append(option);
	            }
	        });

	        //加载封面图片
	        if(blog.cover!=null) {
	        	$("#blog-cover-img").show();
    			$("#blog-cover-img").attr("src", "../upload/"+bid+"/"+blog.cover);
	        }
		});		

		//间隔一段时间，自动保存博文内容，以防丢失
		setInterval(function() {
			var content=$("#edit-blog-content").summernote("code");
			if(content==null||content=="") {
				return;
			}
			$.messager.popup("Automatically Saving...");
			BlogManager.backgroudSaving(bid, content, function() {
				$.messager.popup("Blog content saved");
			});
		}, AUTOMATICAL_SAVING_INTERVAL);
		
	});
	
	BlogManager.getBlogContent(bid, function(content) {
		$("#edit-blog-content").summernote({
			height: getScreenHeight()-300<MIN_EDIT_HEIGHT? MIN_EDIT_HEIGHT: getScreenHeight()-370,
			callbacks: {
				onImageUpload: function(files, editor, welEditable) {  
		    		uploadIllustration(bid, files[0], this);
		    	}
			}
		}).summernote("code", content);
		$("#loading-blog").hide();
	})

	$("#edit-blog-clear").click(function() {
		$.messager.confirm("Tip", "Confirm to clear title and content of this blog article?", function() {
            $("#edit-blog-title").val("");
            $("#edit-blog-content").summernote("code", "");
        });
	});

	//提交修改
	$("#edit-blog-submit").click(function() {
		var title=$("#edit-blog-title").val();
    	var content=$("#edit-blog-content").summernote("code");
    	var date=$("#edit-blog-date").val();
    	var tid=$("#edit-blog-type").val();
    	var validate=true;
    	if(title==null||title=="") {
            $("#edit-blog-title").parent().addClass("has-error");
            validate=false;
        } else {
            $("#edit-blog-title").parent().removeClass("has-error");
        }
        if(content==null||content=="") {
            validate=false;
        } 
        if(tid==null||tid=="") {
            $("#edit-blog-type").parent().addClass("has-error");
            validate=false;
        } else {
            $("#edit-blog-type").parent().removeClass("has-error");
        }
        if(validate) {
        	$("#edit-blog-submit").html('<i class="fa fa-refresh fa-spin"></i>')
        						  .attr("disabled", "disabled");
	    	BlogManager.modifyBlog(bid, title, content, date, tid, function() {
	    		$.messager.popup("Mofify this blog successfully!");
	    		setTimeout(function() {
					location.href="list.html";
				}, 1000);
	    	});
        } else {
        	$.messager.popup("No title, content or type!");
        }	
	});

	//绑定上传图片控件
	$("#blog-upload-cover").fileupload({
    	autoUpload:true,
    	url:"../PhotoServlet?task=uploadBlogCover&bid="+bid,
    	dataType:"json",
    	acceptFileTypes: /^image\/(gif|jpeg|png)$/,
    	done:function(e,data){
    		$("#blog-cover-img").show();
    		$("#blog-cover-img").attr("src", "../upload/"+bid+"/"+data.result.cover);
    		setTimeout(function(){
				$("#blog-cover-progress").hide(1500);
			},2000);
    	},
    	progressall:function(e,data){
			$("#blog-cover-progress").show();
		    var progress=parseInt(data.loaded/data.total*100, 10);
		    $("#blog-cover-progress .progress-bar").css("width",progress+"%");
		    $("#blog-cover-progress .progress-bar").text(progress+"%");
    	}
    });

    //删除封面图片
    $("#blog-cover-delete").click(function() {
    	$.messager.confirm("Tip", "Confirm to delete cover of this blog?", function() {
    		BlogManager.deleteCover(bid, function(success) {
	    		if(success) {
	    			$("#blog-cover-img").hide();
	    			$("#blog-cover-img").attr("src","");
	    			$.messager.popup("Cover has deleted!");
	    		}
	    	});
    	});
    });
});

/**
 * summernote上传插图
 * @param bid 博文id
 * @param file 文件
 * @param element summernote元素
 */
function uploadIllustration(bid, file, element) {
	$.messager.popup("Uploading illustration...");
	var formData=new FormData();  
	formData.append("file", file);  
	$.ajax({
        data: formData,
        type: "POST",
        url: "../PhotoServlet?task=uploadIllustration&bid="+bid,
        cache: false,
        contentType: false,
        processData: false,
        success: function(data) {
        	$(element).summernote("insertImage", "../upload/"+bid+"/"+data.filename);
        	$.messager.popup("Illustration uploaded");
        }
    });
}
