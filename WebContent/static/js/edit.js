var bid=request("bid");
var MIN_EDIT_HEIGHT=380;
var units = ["B", "KB", "MB", "GB", "TB"];

$(document).ready(function() {
	checkAdminSession(function() {
		$(window).bind("beforeunload", function() {
			return "Be sure you have saved your blog!";
		});
		
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

	        //加载插图
	        loadIllustrators();

            AttachmentManager.getAttachmentsByBid(bid, function(attachments) {
                for(var i in attachments) {
                    putAttachment(attachments[i]);
                }
            });
		});		
	});
	
	BlogManager.getBlogContent(bid, function(content) {
		$("#edit-blog-content").summernote({
			height: getScreenHeight()-300<MIN_EDIT_HEIGHT? MIN_EDIT_HEIGHT: getScreenHeight()-340,
			toolbar: SUMMERNOTE_TOOLBAR_FULL,
			callbacks: {
				onImageUpload: function(files, editor, welEditable) {  
		    		uploadIllustration(bid, files[0], this);
		    	}
			}
		}).summernote("code", content);
		$("#loading-blog").hide();
	})

	//清除
	$("#edit-blog-clear").click(function() {
		$.messager.confirm("Tip", "Confirm to clear title and content of this blog article?", function() {
            $("#edit-blog-title").val("");
            $("#edit-blog-content").summernote("code", "");
        });
	});

	//保存修改
	//间隔一段时间，自动保存博文内容，以防丢失
	$("#edit-blog-save").click(function() {
		var content=$("#edit-blog-content").summernote("code");
		if(content==null||content=="") {
			return;
		}
		$(this).html($("<i>").addClass("fa fa-spin fa-refresh")).attr("disabled", "disabled");
		BlogManager.backgroudSaving(bid, content, function() {
			$("#edit-blog-save").html("Save").removeAttr("disabled");
			$.messager.popup("Blog content saved");
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
	    		$(window).unbind("beforeunload");
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
    	url:"../UploadServlet?task=uploadBlogCover&bid="+bid,
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
	    		} else {
	    			$.messager.popup("Delete failed!");
	    		}
	    	});
    	});
    });
    
    //绑定上传附件控件
    $("#upload-attachment").fileupload({
        autoUpload:true,
        url:"../UploadServlet?task=uploadAttachment&bid="+bid,
        dataType:"json",
        done:function(e,data){
            putAttachment(data.result);
            setTimeout(function(){
                $("#attachment-upload-progress").hide(1500);
            },2000);    
        },
        progressall:function(e,data){
            $("#attachment-upload-progress").show();
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $("#attachment-upload-progress .progress-bar").css("width",progress + "%");
            $("#attachment-upload-progress .progress-bar").text(progress + "%");
        }
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
        url: "../UploadServlet?task=uploadIllustration&bid="+bid,
        cache: false,
        contentType: false,
        processData: false,
        success: function(data) {
        	$(element).summernote("insertImage", "../upload/"+bid+"/"+data.filename);
        	$.messager.popup("Illustration uploaded");
        	//上传图片完成后更新插图管理列表
        	loadIllustrators();
        }
    });
}

/**
 * 加载插图
 */
function loadIllustrators() {
	$("#illustration-list").mengularClear();
    IllustrationManager.getIllustrationsByBid(bid, function(illustrations) {
    	for(var i in illustrations) {
    		var path = location.href.split("admin")[0] + "upload/"+illustrations[i].bid+"/"+illustrations[i].filename;
    		
    		$("#illustration-list").mengular(".illustration-list-template", {
    			iid: illustrations[i].iid,
    			src: path
    		});

    		//点击复制插图链接
    		$("#" + illustrations[i].iid + " .illustration-list-copy").click(function() {
    			var link = $("#" + $(this).mengularId() + " .illustration-list-link");
    			if(link.is(":visible")) {
    				link.fadeOut(function() {
    					$(".illustration-list-link").hide();
    				});
    			} else {
    				$(".illustration-list-link").hide();
    				link.fadeIn();
    			}
    		});
    		
    		//关闭复制插图链接
    		$("#" + illustrations[i].iid + " .illustration-list-link p i").click(function() {
    			$("#" + $(this).mengularId() + " .illustration-list-link").fadeOut();
    		});
    		
			new Clipboard("#copy-" + illustrations[i].iid, {
			    text: function(trigger) {
			        return $("#"+$(trigger).mengularId()+" .thumbnail img").attr("src");
			    }
			}).on("success", function(e) {
				
			});
    		
    		//点击删除插图
    		$("#"+illustrations[i].iid+" .illustration-list-remove").click(function() {
    			var confirm = $("#" + $(this).mengularId() + " .illustration-list-confirm");
    			if(confirm.is(":visible")) {
    				confirm.fadeOut(function() {
    					$(".illustration-list-confirm").hide();
    				});
    			} else {
    				$(".illustration-list-confirm").hide();
    				confirm.fadeIn();
    			}
    		});

    		//确认删除
    		$("#" + illustrations[i].iid + " .illustration-list-confirm-yes").click(function() {
    			var iid=$(this).mengularId();
    			IllustrationManager.removeIllustration(iid, function(success) {
    				if(success) {
    					$("#"+iid).fadeOut(function() {
    						$(this).remove();
    					});
    				} else {
    					$.messager.popup("Delete failed!");
    				}
    			});
    		});

    		//取消删除
    		$("#" + illustrations[i].iid + " .illustration-list-confirm p i, " + 
    		  "#" + illustrations[i].iid + " .illustration-list-confirm-no").click(function() {
    		  	$("#" + $(this).mengularId() + " .illustration-list-confirm").fadeOut();
    		});
    	}
	});
}

function putAttachment(attachment) {
    var size = attachment.size;
    var unit = 0;
    while(size >= 1024) {
        size /= 1024;
        unit ++;
    }

    $("#attachment-list").mengular(".attachment-list-template", {
        aid: attachment.aid,
        filename: attachment.filename,
        upload: attachment.upload == null ? new Date(): attachment.upload,
        size: size.toFixed(2) + " " + units[unit]
    });

    $("#" + attachment.aid + " .attachment-list-remove").click(function() {
        var aid = $(this).mengularId();
        AttachmentManager.removeAttachment(aid, function(success) {
            if(success) {
                $("#" + aid).remove();
            } else {
                $.messager.popup("Delete attachement failed.")
            }
        });
    });
}
