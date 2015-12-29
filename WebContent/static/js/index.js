$(document).ready(function($) {
	$(".scroll").click(function(event){		
		event.preventDefault();
		$('html,body').animate({scrollTop:$(this.hash).offset().top},1000);
	});
	addEventListener("load", function() { 
		setTimeout(hideURLbar, 0); 
	}, false); 
	var pull 		= $('#pull');
	menu 		= $('nav ul');
	menuHeight	= menu.height();
	$(pull).on('click', function(e) {
		e.preventDefault();
		menu.slideToggle();
	});
	$(window).resize(function(){
		var w = $(window).width();
		if(w > 320 && menu.is(':hidden')) {
			menu.removeAttr('style');
		}
	});
	$().UItoTop({ easingType: 'easeOutQuart' });
	$("#works").load("work.html");
});

function hideURLbar()
{ 
	window.scrollTo(0,1); 
} 