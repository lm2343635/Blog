//模态框原始宽度，照片显示的真实宽度要根据原始宽度进行调节
var MODAL_ORIGINAL_WIDTH=600;
var MODAL_LG_ORIGINAL_WIDTH=900;
//顶部导航栏高度
var HEADER_MENU_HEIGHT=32;
//多照片浏览控制器高度
var CONTROLLER_HEIGHT=75;
var CONTROLLER_WIDTH=100;
//默认放大倍数
var DEFAULT_MAGNIFYING=1.2;
//默认水平占比
var DEFAULT_HORIZONTAL_PROPORTION=0.8
//多张照片预览时，水平放六个照片缩略图
var HORIZONTAL_PHOTO_NUM=6;
//多张照片浏览控制器缩略图最多7个，只能为奇数
var CONTROLLER_MAX_THUMBNAILS=7;

//单个照片显示器html文档
var OnePhotoModal=
	'<div class="modal fade" id="one-photo-modal" tabindex="-1">'+
		'<div class="modal-dialog">'+
			'<div class="modal-content" id="one-photo">'+
				'<img class="img-rounded">'+
			'</div>'+
		'</div>'+
	'</div>';

/**
 * 使用Bootstrap模态框显示照片
 * @param src 照片路径
 * @param horizontalProportion 水平占比
 */
function showOnePhoto(src,horizontalProportion)
{
	if(horizontalProportion==null)
		horizontalProportion=DEFAULT_HORIZONTAL_PROPORTION;
	//将html文档添加到body中
	$("body").append(OnePhotoModal);
	//去掉图片区域边框
	$("#one-photo").css("border","0");
	//加载资源
	$("#one-photo img").attr("src",src);
	//在没有调整好位置的时候暂时隐藏图片
	$("#one-photo img").fadeOut(0);
	//显示模态框
	$("#one-photo-modal").modal("show");
	//模态框加载后开始调整图片的位置
	$("#one-photo-modal").on("shown.bs.modal",function(e){
		//照片定位
		var width=$("#one-photo img").width();
		var height=$("#one-photo img").height();	
		locationPhoto(width,height,getScreenWidth(), getScreenHeight(), horizontalProportion, HEADER_MENU_HEIGHT, "#one-photo img",MODAL_ORIGINAL_WIDTH);
		//照片尺寸和位置调节完毕，显示照片
		$("#one-photo img").fadeIn("slow");
		$(window).resize(function(){
			locationPhoto(width,height,getScreenWidth(), getScreenHeight(), horizontalProportion, HEADER_MENU_HEIGHT, "#one-photo img",MODAL_ORIGINAL_WIDTH);
		});
	});
	//当模态框关闭时，删除模态框
	$("#one-photo-modal").on("hidden.bs.modal",function(e){
		$(this).remove();
	});
}

/**
 * 定位照片，照片必须放在一个父元素div里面
 * @param screenWidth 屏幕宽度
 * @param screenHeight 屏幕高度
 * @param horizontalProportion 水平占比
 * @param topMinMargin 照片距离屏幕顶端最小距离
 * @param seletor 照片的选择器
 * @param modalWidth 模态框宽度
 */
function locationPhoto(width,height,screenWidth,screenHeight,horizontalProportion,topMinMargin,seletor,modalWidth)
{

	//要把照片放在中心位置上，首先要考虑当前屏幕能够提供的最大空间是多少
	//最大宽度为水平占比*屏幕宽度
	var maxWidth=horizontalProportion*screenWidth;
	//最大高度为屏幕高度减去两倍的图片距离屏幕顶端最小距离
	var maxHeight=screenHeight-2*topMinMargin;
	//第一种情况：照片尺寸小于该最大空间，直接居中显示就好了
	if(width<=maxWidth&&height<maxHeight)
	{
		$(seletor).parent().css("left",-(width-modalWidth)/2+"px");
		$(seletor).parent().css("top",(screenHeight-height)/2-HEADER_MENU_HEIGHT+"px");
	}
	//第二种情况：照片溢出，照片长宽比大于等于最大空间的长宽比，最大空间宽度设置为照片宽度
	else if(width>maxWidth&&width/height>=maxWidth/maxHeight)
	{
		//按比例压缩照片
		$(seletor).css("width",maxWidth+"px");
		$(seletor).parent().css("left",-(maxWidth-modalWidth)/2+"px");
		//按比例计算新高度
		var newHeight=maxWidth*height/width;
		$(seletor).css("height",newHeight+"px");
		$(seletor).parent().css("top",(screenHeight-newHeight)/2-HEADER_MENU_HEIGHT+"px");
	}
	//第三种情况：照片溢出，照片长宽比小于最大空间的长宽比，最大空间宽度设置为照片宽度
	else if(height>maxHeight&&width/height<maxWidth/maxHeight)
	{
		$(seletor).css("height",maxHeight+"px");
		var newWidth=maxHeight*width/height;
		$(seletor).css("width",newWidth+"px");
		$(seletor).parent().css("left",-(newWidth-modalWidth)/2+"px");
	}	
}

//显示多张照片的HTML文档
var showPhotosModal=
	'<div class="modal fade" id="show-photos-modal" tabindex="-1">'+
		'<div class="modal-dialog modal-lg">'+
			'<div class="modal-content" id="show-photos">'+
				'<div id="album-title" class="alert alert-info" role="alert">Album</div>'+
				'<table id="show-photos-table">'+
					'<tbody></tbody>'+
				'</table>'+
				'<img id="show-photos-full" class="img-rounded">'+
			'</div>'+
			'<div id="show-photos-controller">'+
				'<div id="previous-photo"><span class="glyphicon glyphicon-chevron-left"></span></div>'+
				'<div id="controller-photos"></div>'+
				'<div id="next-photo"><span class="glyphicon glyphicon-chevron-right"></span></div>'+
			'</div>'+
		'</div>'+
	'</div>';
//多张照片文件路径全局变量
var Srcs;

/**
 * 显示多张照片
 * @param srcs 照片路径数组
 * @param horizontalProportion 水平占比
 */
function showPhotos(srcs,horizontalProportion)
{
	//将当前路径赋值给全局变量
	Srcs=srcs;
	if(horizontalProportion==null)
		horizontalProportion=DEFAULT_HORIZONTAL_PROPORTION;
	//将html文档添加到body中
	$("body").append(showPhotosModal);
	$("#show-photos-table tbody").empty();
	var tr='<tr>';
	for(var i in srcs)
	{
		tr+='<td><img src="'+srcs[i]+'" class="img-rounded show-photos-item" onclick="showFull('+i+')"></td>';
		if(i%HORIZONTAL_PHOTO_NUM==HORIZONTAL_PHOTO_NUM-1||i==srcs.length-1)
		{
			tr+='</tr>'
			$("#show-photos-table tbody").append(tr);
			tr='<tr>';
		}
	}
	$("#show-photos-modal").modal("show");
	$("#show-photos-modal").on("hidden.bs.modal",function(e){
		$(this).remove();
	});
}

function showFull(pid)
{
	$("#album-title").hide();
	$("#show-photos-table").hide();
	$("#show-photos-full").show();
	$("#show-photos-full").attr("src",Srcs[pid]);
	//这个地方也要改成绑定一次，否则也会影响后面切换图片
	$("#show-photos-full").one("load",function(){
		//位置没有调整完成隐藏照片
		$(this).fadeOut(0);
		var width=$(this).width();
		var height=$(this).height();	
		//调整照片位置
		locationPhoto(width, height, getScreenWidth(), getScreenHeight(), DEFAULT_HORIZONTAL_PROPORTION, HEADER_MENU_HEIGHT, this, MODAL_LG_ORIGINAL_WIDTH);
		//位置调整完成，显示照片
		$(this).fadeIn("slow");
		//加载照片控制器
		loadPhotoController(pid,DEFAULT_MAGNIFYING);
	});
}

/**
 * 加载照片控制器
 * @param selected 选中的照片编号
 * @param magnifying 选中照片的放大倍数
 */
function loadPhotoController(selected,magnifying)
{
	if(magnifying==null)
		magnifying==1;
	var start,end;
	if(Srcs.length<7)
	{
		start=0;
		end=Srcs.length;
	}
	else
	{
		if(selected<3)
			start=0;
		else if(selected>Srcs.length-4)
			start=Srcs.length-7;
		else
			start=selected-3;
		end=start+7;
	}
	$("#controller-photos").empty();
	for(var i=start;i<end;i++)
	{
		var td='<img src="'+Srcs[i]+'" class="img-rounded ';
		if(i==selected)
			td+='controller-photo-selected';
		td+=' controller-photo" onclick="changeSelectedPhoto('+i+')">';
		$("#controller-photos").append(td);
	}
	$(".controller-photo-selected").css("width",magnifying*CONTROLLER_WIDTH)
		.css("height",magnifying*CONTROLLER_HEIGHT)
		.css("bottom",(magnifying-1)*CONTROLLER_HEIGHT);
	$("#show-photos-controller").show();
	$("#show-photos-controller").css("left",(MODAL_LG_ORIGINAL_WIDTH-$("#show-photos-controller").width())/2+"px")
		.css("top",(getScreenHeight()-$("#show-photos-controller").height()-HEADER_MENU_HEIGHT+(magnifying-1)*CONTROLLER_HEIGHT)+"px");
	//这两个地方的点击事件也只能绑定一次，因为这个点击事件只会用到一次
	//依次点击完成后当期显示的图片会更换，上一次的点击事件根本就没有用！！！
	$("#previous-photo").one("click",function(){
		var now=parseInt($(".controller-photo-selected").attr("onclick").split("(")[1].split(")")[0]);
		if(now>0)
			changeSelectedPhoto(now-1);
	});
	$("#next-photo").one("click",function(){
		var now=parseInt($(".controller-photo-selected").attr("onclick").split("(")[1].split(")")[0]);
		if(now<Srcs.length-1)
			changeSelectedPhoto(now+1);
	});
}

/**
 * 更改当期被选中，也就是正在浏览的图片
 * @param pid 图片编号
 */
function changeSelectedPhoto(pid)
{
	$("#show-photos-full").css("height","auto").css("width","auto");
	$("#show-photos-full").parent().css("top",0);
	$("#show-photos-full").attr("src",Srcs[pid]);
	/**
	 * 这个地方本来是这么写的，但是这么写绝对会出bug
	 * 	$("#show-photos-full").load(function(){
	 * 		loadPhotoController(pid, DEFAULT_MAGNIFYING);
	 * 	});
	 * 这样去绑定一个load时间在下次其他pid调用该函数依然会执行
	 * 因为他被绑死了，应该只绑定一次
	 */
	$("#show-photos-full").one("load",function(){
		//$(this).fadeOut(0);
		var width=$(this).width();
		var height=$(this).height();	
		locationPhoto(width, height, getScreenWidth(), getScreenHeight(), DEFAULT_HORIZONTAL_PROPORTION, HEADER_MENU_HEIGHT, this, MODAL_LG_ORIGINAL_WIDTH);
		//$(this).fadeIn("slow");
		loadPhotoController(pid, DEFAULT_MAGNIFYING);
	});
}

/**
 * 得到屏幕高度
 * @returns
 */
function getScreenHeight()
{
	return document.documentElement.clientHeight;
}

/**
 * 得到屏幕宽度
 * @returns
 */
function getScreenWidth()
{
	return document.documentElement.clientWidth;
}