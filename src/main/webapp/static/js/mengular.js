/*!
 * mengular v3.3, http://github.com/lm2343635/Mengular
 * ===================================
 * Powerful jQuery plugin for ajax table loading.
 *
 * (c) 2015 - 2016 fczm, http://fczm.pw
 * fczm.pw Licensed.
 */

//define constant
var LEFT_SPLIT_STR = "${",
	RIGHT_SPLIT_STR = "}$",
	MENGULAR_TEMPLATE_CLASS = "mengular-template";

(function($) {
	/**
	 * Using template to load data, core function of mengulat ajax table loading framework
	 * @param template
	 * @param item
	 */
	$.fn["mengular"] = function(template, data) {
		//get outer html content of element
		template = $(template).prop("outerHTML");
		if(template == null) {
			console.log("Cannot find element by this template selector.");
			return;
		}
		//get placeholders by spliting html document
		var htmlArray = template.split(LEFT_SPLIT_STR);
		var placeholders = new Array();
		for(var i = 1; i < htmlArray.length; i++) {
			//placeholder is like "${placeholder name}$"
			placeholders[i-1] = htmlArray[i].split(RIGHT_SPLIT_STR)[0];
		}
		//handle data for array
		if(data instanceof Array) {
			var output = "";
			for(var i in data) {
				output += generateItem(template, placeholders, data[i]);;
			}
			$(this).append(output);
			return;
		} else {
			$(this).append(generateItem(template, placeholders, data));
		}
	};

	/**
	 * Clear all items
	 */
	$.fn["mengularClear"] = function() {
		$(this).children().each(function() {
			if(!$(this).hasClass(MENGULAR_TEMPLATE_CLASS))
				$(this).remove();
		});
	};

	/**
	 * Remove template
	 */
	$.fn["mengularClearTemplate"] = function() {
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
	$.fn["fillText"]=function(data) {
		var html = $(this).prop("outerHTML");
		for(var key in data) {
			do {
				html = html.replace("@{"+key+"}", data[key]);
			} while(html.search("@{"+key+"}") != -1);
		}
		$(this).prop("outerHTML", html);
	};

	/**
	 * Find template id from any element of a template
	 */ 
	$.fn["mengularId"] = function() {
		var parent = $(this).parent();
		while(parent.attr("id") == undefined) {
			parent = parent.parent();
		}
		return parent.attr("id");
	}
})(window.jQuery);

/**
 * fill text
 * @param data
 */
function fillText(data) {
	for(var attributeName in data) {
		$("#" + attributeName).text(data[attributeName]);
	}
}

/**
 * fill value
 * @param data
 */
function fillValue(data) {
	for(var attributeName in data) {
		$("#" + attributeName).val(data[attributeName]);
	}
}

/**
 * Generate html document for item, used in mengulat core function.
 * @param template  dom element of template
 * @param placeholders
 * @param data
 * @returns
 */
function generateItem(template, placeholders, data) {
	//replace placeholder with data attributes
	for(var i in placeholders) {
		template = template.replace(LEFT_SPLIT_STR + placeholders[i] + RIGHT_SPLIT_STR, eval("data." + placeholders[i]));
	}
	//remove mengular template class
	template = $(template).removeClass(MENGULAR_TEMPLATE_CLASS).prop("outerHTML");
	return template;
}