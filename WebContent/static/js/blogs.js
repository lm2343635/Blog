$(document).ready(function() {
    $("#add-blog-content").summernote({
    	height: 300
    });

    $("#add-blog-submit").click(function() {
    	var markupStr=$("#add-blog-content").summernote('code');
    	console.log(markupStr);
    });
});