var bid = getPageName();
var downloadingAid;

$(document).ready(function () {
    i18n("blog", "../static/i18n/", [
        "blog_blogs",
        "blog_created_in",
        "blog_readers",
        "blog_attachments",
        "blog_attachment_upload_at",
        "blog_attachment_size",
        "blog_visitor_comments",
        "blog_no_comments",
        "blog_write_comment",
        "blog_your_name",
        "blog_comment_submit",
        "blog_back",
        "blog_download_attachment_title",
        "blog_download_attachment_validate",
        "blog_download_attachment_close",
        "blog_download_attachment_submit"
    ]);

    $("#add-comment-content").summernote({
        lang: getLanguage(),
        height: 300,
        toolbar: SUMMERNOTE_TOOLBAR_TEXT_ONLY
    });

    if (!$("#attachment-list div").html()) {
        $("#blog-attachment-title").remove();
    }

    //加载博文信息
    BlogManager.getBlogInfo(bid, true, function (blog) {
        if (blog == null) {
            location.href = "../urlError.html";
            return;
        }
        var cover = (blog.cover != null && blog.bgenable) ? "/files/" + blog.bid + "/" + blog.cover : "/static/images/header-bg.jpg";
        $("#home .top-header").css("background-image", "url(" + cover + ")");
        $("#blog-info").fillText({
            blog_readers_count: blog.readers
        });
    });

    //加载评论
    CommentManager.getCommentsByBid(bid, function (comments) {
        if (comments.length > 0) {
            $("#no-comment").hide();
        }
        for (var i in comments) {
            $("#comment-list").mengular(".comment-list-template", {
                cid: comments[i].cid,
                name: comments[i].name == "" ? "Anonymity" : comments[i].name,
                date: comments[i].date.format(DATE_HOUR_MINUTE_FORMAT),
                content: comments[i].content,
            });
        }
    });

    //提交评论
    $("#add-comment-submit").click(function () {
        var name = $("#add-comment-name").val();
        var content = $("#add-comment-content").summernote("code");
        if (content == null || content == "") {
            $.messager.popup("Wirte some comment, please!");
            return;
        }
        CommentManager.addComment(bid, name, content, function () {
            $.messager.popup("Add Comment Success!");
            setTimeout(function () {
                location.reload();
            }, 1000);
        });
    });

    //为下载按钮绑定事件
    $("#attachment-list .attachment-list-download").each(function () {
        $(this).click(function () {
            downloadingAid = $(this).attr("data-id");
            AttachmentManager.getAttachment(downloadingAid, function (attachment) {
                fillText({
                    "attachment-download-filename": attachment.filename,
                    "attachment-download-upload": attachment.upload.format(DATE_HOUR_MINUTE_SECOND_FORMAT),
                    "attachment-download-size": attachment.size
                });
            });
            $("#attachment-download-validate").attr("src", "/validate/code");
            $("#attachment-download-modal").modal("show");
        });
    });

    //提交验证码，开始下载
    $("#attachment-download-submit").click(function () {
        var code = $("#attachment-download-code").val();
        if (code == "" || code == null) {
            return;
        }
        AttachmentManager.validateDownload(downloadingAid, code, function (token) {
            if (token == null) {
                $("#attachment-download-code").addClass("error");
                return;
            }
            $("#attachment-download-code").removeClass("error");
            location.href = "../UploadServlet?task=downloadByToken&token=" + token;
        });
    });
});