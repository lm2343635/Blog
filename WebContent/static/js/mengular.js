/*!
 * mengular v3.2, http://github.com/lm2343635/Mengular
 * ===================================
 * Powerful jQuery plugin for ajax table loading.
 *
 * (c) 2015 - 2016 Lidaye, http://fczm.pw
 * fczm.pw Licensed
 */

(function($) {
	var _mengular="mengular",
		_clear="mengularClear",
		_clearTemplate="mengularClearTemplate",
		_fillText="fillText",
		_mengularId="mengularId";
		LEFT_SPLIT_STR="${",
		RIGHT_SPLIT_STR="}$",
		MENGULAR_TEMPLATE_CLASS="mengular-template";

	/**
	 * Using template to load item
	 * @param template
	 * @param item
	 */
	$.fn[_mengular]=function(template, item) {
		var templateHtml=$(template).prop("outerHTML");
		var htmlArray=templateHtml.split(LEFT_SPLIT_STR);
		var attributeNames=new Array();
		for(var i=1;i<htmlArray.length;i++) 
			attributeNames[i-1]=htmlArray[i].split(RIGHT_SPLIT_STR)[0];
		for(var j in attributeNames) 
			templateHtml=templateHtml.replace(LEFT_SPLIT_STR+attributeNames[j]+RIGHT_SPLIT_STR,eval("item."+attributeNames[j]));
		templateHtml=$(templateHtml).removeClass(MENGULAR_TEMPLATE_CLASS).prop("outerHTML");
		$(this).append(templateHtml);
	};

	/**
	 * Clear all items
	 */
	$.fn[_clear]=function() {
		$(this).children().each(function() {
			if(!$(this).hasClass(MENGULAR_TEMPLATE_CLASS))
				$(this).remove();
		});
	};

	/**
	 * Remove template
	 */
	$.fn[_clearTemplate]=function() {
		$(this).children().each(function() {
			if($(this).hasClass(MENGULAR_TEMPLATE_CLASS)) {
				$(this).remove();
			}
		});
	};
	
	/**
	 * Fill text using palceholder @{placeholder name}
	 * @prarm data, formate: {
	 * 		placeholder1: value1,
	 * 		placeholder2: value2,
	 * 		...
	 * 		placeholdern: valuen
	 * }
	 * Be careful that this fill text method will reload dom element, so that all event will be removed after using this method to a dom element.
	 */
	$.fn[_fillText]=function(data) {
		var html=$(this).prop("outerHTML");
		for(var key in data) {
			do {
				html=html.replace("@{"+key+"}", data[key]);
			} while(html.search("@{"+key+"}")!=-1);
		}
		$(this).prop("outerHTML", html);
	};

	/**
	 * Find template id from any element of a template
	 */ 
	$.fn[_mengularId]=function() {
		var parent=$(this).parent();
		while(parent.attr("id")==undefined) {
			parent=parent.parent();
		}
		return parent.attr("id");
	}
})(window.jQuery);

/**
 * fill text
 * @param data
 */
function fillText(data) {
	for(var attributeName in data)
		$("#"+attributeName).text(data[attributeName]);
}

/**
 * fill value
 * @param data
 */
function fillValue(data) {
	for(var attributeName in data)
		$("#"+attributeName).val(data[attributeName]);
}