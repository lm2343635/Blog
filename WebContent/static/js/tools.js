
//“年-月-日”格式
var YEAR_MONTH_DATE_FORMAT="yyyy-MM-dd";
//“年-月”格式
var YEAR_MONTH_FORMAT="yyyy-MM";
//“年”格式
var YEAR_FORMAT="yyyy";
//“年-月-日 时:分”格式
var DATE_HOUR_MINUTE_FORMAT="yyyy-MM-dd hh:mm";
//“年-月-日 时:分:秒”格式
var DATE_HOUR_MINUTE_SECOND_FORMAT="yyyy-MM-dd hh:mm:ss";

//“年-月-日”格式 中文
var YEAR_MONTH_DATE_FORMAT_CN="yyyy年MM月dd日";
//“年-月-日 时:分:秒”格式 中文
var DATE_HOUR_MINUTE_FORMAT_CN="yyyy年MM月dd日 hh点mm分";

/**
 * 检查session，并返回userBean
 *  @param doAfterCheck 验证成功回调函数
 */
function checkSession(doAfterCheck) {
	SendeeManager.checkSession(function(user) {
		doAfterCheck(user);
	});
}

/**
 * 检查管理员session，返回管理员名称
 * @param doAfterCheck 验证成功回调函数
 */
function checkAdminSession(doAfterCheck) {
	AdminManager.checkSession(function(username) {
		if(username==null) {
			location.href="sessionError.html";
			return null;
		} else {
			doAfterCheck(username);
		}
	});
}

/**
 * 得到屏幕高度
 * @returns
 */
function getScreenHeight() {
	return document.documentElement.clientHeight;
}

/**
 * 得到屏幕宽度
 * @returns
 */
function getScreenWidth() {
	return document.documentElement.clientWidth;
}

/**
 * 判断是否为数字
 * @param num
 * @returns
 */
function isNum(num) {
     var reNum =/^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/;
     return (reNum.test(num));
}

/**
 * 判断是否为整数
 * @param num
 * @returns
 */
function isInteger(num) {
    var reNum =/^-?[1-9]\d*$/;
    return (reNum.test(num));
}

/**
 * 判断是否为电子邮件格式
 * @param email
 * @returns
 */
function isEmailAddress(email) {
	var patten = new RegExp(/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/);
	return patten.test(email);
}

/**
 * 格式化时间
 * @param format 时间格式
 * @returns
 */
Date.prototype.format =function(format) {
    var o={
	    "M+" : this.getMonth()+1, //month
		"d+" : this.getDate(),    //day
		"h+" : this.getHours(),   //hour
		"m+" : this.getMinutes(), //minute
		"s+" : this.getSeconds(), //second
		"q+" : Math.floor((this.getMonth()+3)/3),  //quarter
		"S" : this.getMilliseconds() //millisecond
    };
    if(/(y+)/.test(format)) 
    	format=format.replace(RegExp.$1,(this.getFullYear()+"").substr(4- RegExp.$1.length));
    for(var k in o)
    	if(new RegExp("("+ k +")").test(format))
    		format = format.replace(RegExp.$1,RegExp.$1.length==1? o[k] :("00"+ o[k]).substr((""+ o[k]).length));
    return format;
};


/**
 * 得到每个月的天数
 * @param year
 * @return
 */
function getMonthDay(year) {
	var monthDay=[31,28,31,30,31,30,31,31,30,31,30,31];
	if(isLeapYear(year))
		monthDay[1]++;
	return monthDay;
}

/**
 * 判断是否闰年
 * @param year
 * @returns {Boolean}
 */
function isLeapYear(year) {
	if(year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
		return true;
	return false;
}

/**
 * 得到两个日期之间的日期差
 * @param start
 * @param end
 */
function getDaysBetweenDates(start, end) {
	return Math.floor((end.getTime()-start.getTime())/(24*3600*1000));
}

/**
 * 闪烁提醒
 * @param selector 选择器
 * @param time 闪烁时间，单位毫秒
 * @param frequency 闪烁频率，单位毫秒
 */
function flickerTip(selector,time,frequency) {
	var flicker=setInterval(function(){
		if($(selector).css("visibility")=="hidden")		
			$(selector).css("visibility","inherit");
		else
			$(selector).css("visibility","hidden");
	}, frequency);
	setTimeout(function(){
		clearInterval(flicker);
		$(selector).css("visibility","inherit");
	}, time);
}

/**
 * 接收request参数
 * @param paras
 * @returns
 */
function request(paras) { 
    var url = location.href; 
    var paraString = url.substring(url.indexOf("?")+1,url.length).split("&"); 
    var paraObj = {} 
    for (i=0; j=paraString[i]; i++)
    	paraObj[j.substring(0,j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=")+1,j.length); 
    var returnValue = paraObj[paras.toLowerCase()]; 
    if(typeof(returnValue)=="undefined")
    	return ""; 
    else
    	return returnValue; 
} 

/**
* 得到本月起始日期
* @returns {String}
*/
function getThisMonthStart() {
	var nowMonth=new Date().format("yyyy-MM");
	return nowMonth+"-01";
}

/**
* 得到本月结束日期
* @returns {String}
*/
function getThisMonthEnd() {
	var nowMonth=new Date().format("yyyy-MM");
	var year=parseInt(nowMonth.split("-")[0]);
	var month=parseInt(nowMonth.split("-")[1]);
	var monthDay=getMonthDay(year);
	return nowMonth+"-"+monthDay[month-1];
}

/**
 * 得到本年起始日期
 * @returns {String}
 */
function getThisYearStart() {
	var nowYear=new Date().format("yyyy");
	return nowYear+"-01-01";
}


/**
 * 得到本年结束
 * @returns {String}
 */
function getThisYearEnd() {
	var nowYear=new Date().format("yyyy");
	return nowYear+"-12-31";
}

/**
 * 只保留一个字符串中的前n个字符
 * @param str
 * @param n
 * @returns
 */
function substr(str, n) {
	if(str.length>n)
		return str.substr(0, n)+"...";
	else
		return str;
}
